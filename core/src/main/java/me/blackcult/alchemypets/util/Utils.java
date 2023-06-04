package me.blackcult.alchemypets.util;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");

    public static String color(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        StringBuffer buffer = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static List<String> color(List<String> list) {
        List<String> colorList = new ArrayList<>();
        for(String string : list) {
            colorList.add(color(string));
        }
        return colorList;
    }
}
