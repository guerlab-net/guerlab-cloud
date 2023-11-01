/*
 * Copyright 2018-2024 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ibatis.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Clinton Begin
 */
@SuppressWarnings("unused")
public enum JdbcType {
	/**
	 * ARRAY.
	 */
	ARRAY(Types.ARRAY),

	/**
	 * BIT.
	 */
	BIT(Types.BIT),

	/**
	 * TINYINT.
	 */
	TINYINT(Types.TINYINT),

	/**
	 * SMALLINT.
	 */
	SMALLINT(Types.SMALLINT),

	/**
	 * INTEGER.
	 */
	INTEGER(Types.INTEGER),

	/**
	 * BIGINT.
	 */
	BIGINT(Types.BIGINT),

	/**
	 * FLOAT.
	 */
	FLOAT(Types.FLOAT),

	/**
	 * REAL.
	 */
	REAL(Types.REAL),

	/**
	 * DOUBLE.
	 */
	DOUBLE(Types.DOUBLE),

	/**
	 * NUMERIC.
	 */
	NUMERIC(Types.NUMERIC),

	/**
	 * DECIMAL.
	 */
	DECIMAL(Types.DECIMAL),

	/**
	 * CHAR.
	 */
	CHAR(Types.CHAR),

	/**
	 * VARCHAR.
	 */
	VARCHAR(Types.VARCHAR),

	/**
	 * LONGVARCHAR.
	 */
	LONGVARCHAR(Types.LONGVARCHAR),

	/**
	 * DATE.
	 */
	DATE(Types.DATE),

	/**
	 * TIME.
	 */
	TIME(Types.TIME),

	/**
	 * TIMESTAMP.
	 */
	TIMESTAMP(Types.TIMESTAMP),

	/**
	 * BINARY.
	 */
	BINARY(Types.BINARY),

	/**
	 * VARBINARY.
	 */
	VARBINARY(Types.VARBINARY),

	/**
	 * LONGVARBINARY.
	 */
	LONGVARBINARY(Types.LONGVARBINARY),

	/**
	 * NULL.
	 */
	NULL(Types.NULL),

	/**
	 * OTHER.
	 */
	OTHER(Types.OTHER),

	/**
	 * BLOB.
	 */
	BLOB(Types.BLOB),

	/**
	 * CLOB.
	 */
	CLOB(Types.CLOB),

	/**
	 * BOOLEAN.
	 */
	BOOLEAN(Types.BOOLEAN),

	/**
	 * CURSOR.
	 */
	CURSOR(-10), // Oracle

	/**
	 * UNDEFINED.
	 */
	UNDEFINED(Integer.MIN_VALUE + 1000),

	/**
	 * NVARCHAR.
	 */
	NVARCHAR(Types.NVARCHAR), // JDK6

	/**
	 * NCHAR.
	 */
	NCHAR(Types.NCHAR), // JDK6

	/**
	 * NCLOB.
	 */
	NCLOB(Types.NCLOB), // JDK6

	/**
	 * STRUCT.
	 */
	STRUCT(Types.STRUCT),

	/**
	 * JAVA_OBJECT.
	 */
	JAVA_OBJECT(Types.JAVA_OBJECT),

	/**
	 * DISTINCT.
	 */
	DISTINCT(Types.DISTINCT),

	/**
	 * REF.
	 */
	REF(Types.REF),

	/**
	 * DATALINK.
	 */
	DATALINK(Types.DATALINK),

	/**
	 * ROWID.
	 */
	ROWID(Types.ROWID), // JDK6

	/**
	 * LONGNVARCHAR.
	 */
	LONGNVARCHAR(Types.LONGNVARCHAR), // JDK6

	/**
	 * SQLXML.
	 */
	SQLXML(Types.SQLXML), // JDK6

	/**
	 * SQLXML.
	 */
	DATETIMEOFFSET(-155), // SQL Server 2008

	/**
	 * TIME_WITH_TIMEZONE.
	 */
	TIME_WITH_TIMEZONE(Types.TIME_WITH_TIMEZONE), // JDBC 4.2 JDK8

	/**
	 * TIMESTAMP_WITH_TIMEZONE.
	 */
	TIMESTAMP_WITH_TIMEZONE(Types.TIMESTAMP_WITH_TIMEZONE); // JDBC 4.2 JDK8

	private static final Map<Integer, JdbcType> codeLookup = new HashMap<>();

	static {
		for (JdbcType type : JdbcType.values()) {
			codeLookup.put(type.TYPE_CODE, type);
		}
	}

	public final int TYPE_CODE;

	JdbcType(int code) {
		this.TYPE_CODE = code;
	}

	public static JdbcType forCode(int code) {
		return codeLookup.get(code);
	}

}

