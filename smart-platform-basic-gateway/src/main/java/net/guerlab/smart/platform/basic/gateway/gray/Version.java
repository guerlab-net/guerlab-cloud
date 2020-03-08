package net.guerlab.smart.platform.basic.gateway.gray;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * 版本号
 *
 * @author guer
 */
@Getter
@EqualsAndHashCode
public class Version implements Comparable<Version> {

    private static final String X = "x";

    /**
     * 匹配方式
     */
    private VersionMatchType matchType;

    /**
     * 主版本号
     */
    private String major;

    /**
     * 次版本号
     */
    private String minor;

    /**
     * 修订版本号
     */
    private String revision;

    /**
     * 扩展版本号
     */
    private String ext;

    private Version() {

    }

    /**
     * 解析版本号
     *
     * @param string
     *         版本号字符串
     * @param ignoreMatchType
     *         忽略匹配方式
     * @return 版本号对象
     */
    public static Version parse(String string, boolean ignoreMatchType) {
        if (string == null) {
            return null;
        }
        Version version = new Version();

        if (string.startsWith("+")) {
            if (!ignoreMatchType) {
                version.matchType = VersionMatchType.UP;
            }
            string = string.substring(1);
        } else if (string.startsWith("-")) {
            if (!ignoreMatchType) {
                version.matchType = VersionMatchType.DOWN;
            }
            string = string.substring(1);
        } else {
            if (!ignoreMatchType) {
                version.matchType = VersionMatchType.EQUALS;
            }
        }

        String[] strings = string.split("\\.");
        try {
            if (strings.length >= 1) {
                version.major = parseVal(strings[0]);
            }
            if (strings.length >= 2) {
                version.minor = parseVal(strings[1]);
            }
            if (strings.length >= 3) {
                version.revision = parseVal(strings[2]);
            }
            if (strings.length >= 4) {
                version.ext = StringUtils.join(Arrays.copyOfRange(strings, 3, strings.length), ".");
            }
            return version;
        } catch (Exception ignore) {
            return null;
        }
    }

    private static String parseVal(String val) {
        return X.equalsIgnoreCase(val) ? X : String.valueOf(Integer.parseInt(val));
    }

    private static boolean valNotMatch(String v1, String v2, VersionMatchType matchType) {
        if (X.equalsIgnoreCase(v1) || X.equalsIgnoreCase(v2)) {
            return false;
        }

        if (matchType == VersionMatchType.UP) {
            return Integer.parseInt(v1) < Integer.parseInt(v2);
        } else if (matchType == VersionMatchType.DOWN) {
            return Integer.parseInt(v1) > Integer.parseInt(v2);
        } else {
            return !Objects.equals(v1, v2);
        }
    }

    private static Integer compareTo0(String v1, String v2) {
        if (v1 == null && v2 == null) {
            return 0;
        } else if (v1 == null) {
            return 1;
        } else if (v2 == null) {
            return -1;
        } else if (!Objects.equals(v1, v2)) {
            return v1.compareTo(v2);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Version[");
        builder.append(matchType.display);
        if (major == null) {
            builder.append("unknow]");
            return builder.toString();
        }
        builder.append(major);

        if (minor == null) {
            return builder.append("]").toString();
        }
        builder.append(".");
        builder.append(minor);

        if (revision == null) {
            return builder.append("]").toString();
        }
        builder.append(".");
        builder.append(revision);

        if (ext == null) {
            return builder.append("]").toString();
        }
        builder.append(".");
        builder.append(ext);

        return builder.append("]").toString();
    }

    @Override
    public int compareTo(Version o) {
        if (o == null) {
            return 1;
        }

        Integer majorCompareTo = compareTo0(o.major, major);
        if (majorCompareTo != null) {
            return majorCompareTo;
        }

        Integer minorCompareTo = compareTo0(o.minor, minor);
        if (minorCompareTo != null) {
            return minorCompareTo;
        }

        Integer revisionCompareTo = compareTo0(o.revision, revision);
        if (revisionCompareTo != null) {
            return revisionCompareTo;
        }

        if (o.ext == null && ext == null) {
            return 0;
        } else if (o.ext == null) {
            return 1;
        } else if (ext == null) {
            return -1;
        } else {
            return ext.compareTo(o.ext);
        }
    }

    /**
     * 判断两个版本号对象是否匹配
     *
     * @param o
     *         另一版本号对象
     * @return 是否匹配
     */
    public boolean match(Version o) {
        if (o == null) {
            return false;
        }

        if (o.major == null && major == null) {
            return true;
        } else if (o.major == null) {
            return false;
        } else if (major == null) {
            return false;
        } else if (valNotMatch(o.major, major, matchType)) {
            return false;
        } else if (o.minor != null && minor != null && valNotMatch(o.minor, minor, matchType)) {
            return false;
        } else if (o.revision != null && revision != null && valNotMatch(o.revision, revision, matchType)) {
            return false;
        } else {
            return o.ext == null || ext == null || Objects.equals(o.ext, ext);
        }
    }

    /**
     * 匹配方式
     */
    @Getter
    public enum VersionMatchType {
        /**
         * 向上
         */
        UP("+", "+"),
        /**
         * 向下
         */
        DOWN("-", "-"),
        /**
         * 相等
         */
        EQUALS(null, "");

        private String symbol;

        private String display;

        VersionMatchType(String symbol, String display) {
            this.symbol = symbol;
            this.display = display;
        }
    }
}
