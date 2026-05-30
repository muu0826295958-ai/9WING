package com.yg.by9wingbot

import android.content.Context
import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object RemoteServerConfig {

    // เปลี่ยนลิงก์นี้เป็น RAW servers.json ของน้า
    private const val SERVER_LIST_URL =
        "https://raw.githubusercontent.com/USER/REPO/main/servers.json"

    private const val PREF_NAME = "remote_server_config"
    private const val PREF_JSON = "servers_json_cache"

    fun loadOnAppStart(
        context: Context,
        onDone: (servers: List<ProxyList.ServerInfo>, fromRemote: Boolean, message: String) -> Unit
    ) {
        thread(name = "9WING-LoadServerList") {
            val result = runCatching {
                val json = downloadText(SERVER_LIST_URL)
                val servers = parseServers(json)
                if (servers.isEmpty()) error("remote list is empty")
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putString(PREF_JSON, json)
                    .apply()
                Triple(servers, true, "โหลด Server list ออนไลน์สำเร็จ ${servers.size} รายการ")
            }.recoverCatching {
                val cached = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                    .getString(PREF_JSON, null)

                if (!cached.isNullOrBlank()) {
                    val servers = parseServers(cached)
                    if (servers.isNotEmpty()) {
                        Triple(servers, false, "โหลดออนไลน์ไม่สำเร็จ ใช้ cache ล่าสุด ${servers.size} รายการ")
                    } else {
                        Triple(ProxyList.fallbackServers, false, "cache ว่าง ใช้ fallback ในแอพ")
                    }
                } else {
                    Triple(ProxyList.fallbackServers, false, "โหลดออนไลน์ไม่สำเร็จ ใช้ fallback ในแอพ")
                }
            }.getOrElse {
                Triple(ProxyList.fallbackServers, false, "ใช้ fallback ในแอพ")
            }

            ProxyList.update(result.first)

            Handler(Looper.getMainLooper()).post {
                onDone(result.first, result.second, result.third)
            }
        }
    }

    private fun downloadText(url: String): String {
        val conn = (URL(url).openConnection() as HttpURLConnection).apply {
            connectTimeout = 8000
            readTimeout = 8000
            requestMethod = "GET"
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Cache-Control", "no-cache")
        }

        return try {
            val code = conn.responseCode
            if (code !in 200..299) error("HTTP $code")
            conn.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
        } finally {
            conn.disconnect()
        }
    }

    private fun parseServers(json: String): List<ProxyList.ServerInfo> {
        val root = JSONObject(json)
        val arr = root.getJSONArray("servers")
        val result = ArrayList<ProxyList.ServerInfo>()

        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            val enabled = obj.optBoolean("enabled", true)
            if (!enabled) continue

            val host = obj.optString("host", "").trim()
            if (host.isBlank()) continue

            result.add(
                ProxyList.ServerInfo(
                    sid = obj.optInt("sid", 0),
                    name = obj.optString("name", "S${obj.optInt("sid", 0)}"),
                    host = host,
                    port = obj.optInt("port", 8001),
                    enabled = enabled
                )
            )
        }

        return result
    }
}
