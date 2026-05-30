package com.yg.by9wingbot

object ProxyList {
    data class ServerInfo(
        val sid: Int,
        val name: String,
        val host: String,
        val port: Int = 8001,
        val enabled: Boolean = true
    ) {
        override fun toString(): String = "$name | $host:$port"
    }

    val fallbackServers: List<ServerInfo> = listOf(
        ServerInfo(337, "S337", "43.134.81.182", 8001, true),
        ServerInfo(336, "S336", "43.156.118.165", 8001, true),
        ServerInfo(335, "S335", "43.134.44.98", 8001, true),
        ServerInfo(334, "S334", "129.226.202.80", 8001, true),
        ServerInfo(333, "S333", "43.156.52.125", 8001, true),
        ServerInfo(332, "S332", "43.163.117.193", 8001, true),
        ServerInfo(331, "S331", "43.134.180.253", 8001, true),
        ServerInfo(330, "S330", "43.156.72.42", 8001, true),
        ServerInfo(329, "S329", "43.134.250.5", 8001, true),
        ServerInfo(328, "S328", "129.226.209.228", 8001, true),
        ServerInfo(327, "S327", "43.134.78.244", 8001, true),
        ServerInfo(326, "S326", "43.134.239.192", 8001, true),
        ServerInfo(325, "S325", "43.156.232.8", 8001, true),
        ServerInfo(324, "S324", "43.159.51.98", 8001, true),
        ServerInfo(323, "S323", "43.134.89.218", 8001, true),
        ServerInfo(322, "S322", "43.134.45.222", 8001, true),
        ServerInfo(321, "S321", "43.134.176.230", 8001, true),
        ServerInfo(320, "S320", "43.156.138.136", 8001, true),
        ServerInfo(319, "S319", "43.156.97.101", 8001, true),
        ServerInfo(318, "S318", "43.134.74.245", 8001, true),
        ServerInfo(317, "S317", "150.109.21.123", 8001, true),
        ServerInfo(316, "S316", "43.134.55.94", 8001, true),
        ServerInfo(315, "S315", "43.128.81.232", 8001, true),
        ServerInfo(314, "S314", "43.163.84.169", 8001, true),
        ServerInfo(313, "S313", "43.128.81.232", 8001, true),
        ServerInfo(312, "S312", "43.156.116.80", 8001, true),
        ServerInfo(311, "S311", "43.153.211.175", 8001, true),
        ServerInfo(310, "S310", "43.163.89.13", 8001, true),
        ServerInfo(281, "S281", "129.226.211.74", 8001, true),
        ServerInfo(279, "S279", "129.226.211.74", 8001, true),
        ServerInfo(278, "S278", "129.226.211.74", 8001, true),
        ServerInfo(253, "S253", "43.134.95.66", 8001, true),
        ServerInfo(222, "S222", "43.163.89.134", 8001, true),
        ServerInfo(207, "S207", "43.128.106.37", 8001, true),
        ServerInfo(199, "S199", "43.134.18.229", 8001, true),
        ServerInfo(185, "S185", "43.163.113.16", 8001, true),
        ServerInfo(177, "S177", "129.226.196.58", 8001, true),
        ServerInfo(174, "S174", "129.226.196.58", 8001, true),
        ServerInfo(150, "S150", "43.156.116.12", 8001, true),
        ServerInfo(125, "S125", "43.134.94.77", 8001, true),
        ServerInfo(98, "S98", "43.156.174.110", 8001, true),
        ServerInfo(81, "S81", "43.156.134.62", 8001, true),
        ServerInfo(80, "S80", "43.156.134.62", 8001, true),
        ServerInfo(64, "S64", "43.156.57.97", 8001, true),
        ServerInfo(18, "S18", "119.28.105.37", 8001, true),
        ServerInfo(5, "S5", "43.134.228.198", 8001, true)
    )

    @Volatile
    var servers: List<ServerInfo> = fallbackServers

    fun update(newServers: List<ServerInfo>) {
        servers = newServers.filter { it.enabled && it.host.isNotBlank() }
            .ifEmpty { fallbackServers }
    }

    fun getDefault(): ServerInfo = servers.firstOrNull() ?: fallbackServers.first()

    fun getBySid(sid: Int): ServerInfo? = servers.firstOrNull { it.sid == sid }

    fun displayNames(): Array<String> = servers.map { "${it.name} | ${it.host}:${it.port}" }.toTypedArray()
}
