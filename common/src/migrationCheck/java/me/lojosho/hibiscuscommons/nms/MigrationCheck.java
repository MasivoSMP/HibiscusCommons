package me.lojosho.hibiscuscommons.nms;

import java.util.Map;

/** Run with :common:migrationCheck; does not start a server. */
public final class MigrationCheck {
    public static void main(String[] args) throws Exception {
        var parser = NMSHandlers.class.getDeclaredMethod("getMinecraftVersion", String.class);
        parser.setAccessible(true);
        for (String input : new String[]{"26.2", "26.2-local", "26.2.build.1-stable", "26.2.build.1"}) {
            assert MinecraftVersion.fromVersionString((String) parser.invoke(null, input)) == MinecraftVersion.v26_2 : input;
        }
        assert parser.invoke(null, "1.21.11-R0.1-SNAPSHOT").equals("1.21.11");
        assert parser.invoke(null, "26.1.2.build.51-beta").equals("26.1.2");
        assert MinecraftVersion.v26_2.isHigher(MinecraftVersion.v26_1_2);
        assert MinecraftVersion.fromVersionString("26.3") == null;
        var field = NMSHandlers.class.getDeclaredField("VERSION_MAP");
        field.setAccessible(true);
        var versions = (Map<?, ?>) field.get(null);
        assert versions.size() == 11 : "Older adapter registrations must remain present";
        var target = versions.get(MinecraftVersion.v26_2);
        var reference = target.getClass().getDeclaredMethod("internalReference");
        reference.setAccessible(true);
        assert reference.invoke(target).equals("v26_2_R1");
        System.out.println("26.2 version parsing and adapter registration checks passed");
    }
}
