/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.signavio.feel.lib.type.string;

import com.gs.dmn.feel.lib.type.time.BaseDateTimeLib;
import com.gs.dmn.signavio.feel.lib.SignavioUtil;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gs.dmn.feel.lib.FormatUtils.DECIMAL_FORMAT;

public class DefaultSignavioStringLib implements SignavioStringLib {
    @Override
    public Integer len(String text) {
        return text == null ? null : text.length();
    }

    @Override
    public String stringAdd(String first, String second) {
        if (first == null && second == null) {
            return "";
        } else if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else {
            return first + second;
        }
    }

    @Override
    public String concat(List<?> texts) {
        if (!SignavioUtil.areNullSafe(texts) || texts.isEmpty()) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for(Object o: texts) {
            if (o instanceof String) {
                result.append(o);
            } else {
                return null;
            }
        }
        return result.toString();
    }

    @Override
    public String mid(String text, Number start, Number numChar) {
        if (!SignavioUtil.areNullSafe(text, start, numChar) || start.intValue() < 0 || start.intValue() >= text.length() || numChar.intValue() < 0)  {
            return null;
        }

        int s = start.intValue();
        int endIndex = s + numChar.intValue();
        if (endIndex > text.length()) {
            endIndex = text.length();
        }

        return text.substring(s, endIndex);
    }

    @Override
    public String left(String text, Number numChar) {
        if (!SignavioUtil.areNullSafe(text, numChar) || numChar.intValue() < 0) {
            return null;
        }

        int length = numChar.intValue();
        if (length > text.length()) {
            length = text.length();
        }
        return text.substring(0, length);
    }

    @Override
    public String right(String text, Number numChar) {
        if (!SignavioUtil.areNullSafe(text, numChar) || numChar.intValue() < 0) {
            return null;
        }

        int length = numChar.intValue();
        if (length > text.length()) {
            length = text.length();
        }
        return text.substring(text.length() - length);
    }

    @Override
    public String text(Number num, String formatText) {
        if (!SignavioUtil.areNullSafe(num, formatText)) {
            return null;
        }

        DecimalFormat df = new DecimalFormat(formatText, new DecimalFormatSymbols(Locale.US));
        return df.format(num);
    }

    @Override
    public Integer textOccurrences(String findText, String withinText) {
        if (!SignavioUtil.areNullSafe(findText, withinText)) {
            return null;
        }

        Pattern pattern = Pattern.compile(findText);
        Matcher matcher = pattern.matcher(withinText);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    @Override
    public Boolean isAlpha(String text) {
        if (text == null) {
            return null;
        }

        return StringUtils.isAlpha(text);
    }

    @Override
    public Boolean isAlphanumeric(String text) {
        if (!SignavioUtil.areNullSafe(text)) {
            return null;
        }

        return StringUtils.isAlphanumeric(text);
    }

    @Override
    public Boolean isNumeric(String text) {
        if (!SignavioUtil.areNullSafe(text)) {
            return null;
        }

        return StringUtils.isNumeric(text);
    }

    @Override
    public Boolean isSpaces(String text) {
        if (!SignavioUtil.areNullSafe(text)) {
            return null;
        }

        if (text.isEmpty()) {
            return false;
        }
        return StringUtils.isBlank(text);
    }

    @Override
    public String lower(String text) {
        return text == null ? null : text.toLowerCase();
    }

    @Override
    public String trim(String text) {
        return text == null ? null : text.trim().replaceAll(" +", " ");
    }

    @Override
    public String upper(String text) {
        return text == null ? null : text.toUpperCase();
    }

    @Override
    public Boolean contains(String string, String match) {
        if (!SignavioUtil.areNullSafe(string, match)) {
            return null;
        }

        return string.contains(match);
    }

    @Override
    public Boolean startsWith(String string, String match) {
        if (!SignavioUtil.areNullSafe(string, match)) {
            return null;
        }

        return string.startsWith(match);
    }

    @Override
    public Boolean endsWith(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        return string.endsWith(match);
    }

    @Override
    public String string(Object from) {
        if (from == null) {
            return "null";
        } else if (from instanceof Double) {
            return DECIMAL_FORMAT.get().format(from);
        } else if (from instanceof BigDecimal decimal) {
            return decimal.toPlainString();
        } else if (from instanceof LocalDate date) {
            return date.format(BaseDateTimeLib.FEEL_DATE);
        } else if (from instanceof OffsetTime time) {
            return time.format(BaseDateTimeLib.FEEL_TIME);
        } else if (from instanceof ZonedDateTime time) {
            return time.format(BaseDateTimeLib.FEEL_DATE_TIME);
        } else if (from instanceof XMLGregorianCalendar) {
            return from.toString();
        } else {
            return from.toString();
        }
    }
}
