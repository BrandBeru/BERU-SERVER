package org.beru.server.beruserver.model;

import org.beru.server.beruserver.resources.Information;

public class UserInfo {
    private Long id;
    private String name;
    private String osName;
    private String osVersion;
    private String osArch;

    public UserInfo(Long id, String name, String osName, String osVersion, String osArch) {
        this.id = id;
        this.name = name;
        this.osName = osName;
        this.osVersion = osVersion;
        this.osArch = osArch;
    }

    public UserInfo(Long id) {
        this.id = id;
        this.name = Information.USER_NAME;
        this.osName = Information.OS_NAME;
        this.osVersion = Information.OS_VERSION;
        this.osArch = Information.OS_ARCH;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    @Override
    public String toString() {
        return "user_info[" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", osName:'" + osName + '\'' +
                ", osVersion:'" + osVersion + '\'' +
                ", osArch:'" + osArch + '\'' +
                ']';
    }
}
