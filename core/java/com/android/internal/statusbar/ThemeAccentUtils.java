/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.statusbar;

import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.os.RemoteException;

public class ThemeAccentUtils {

    public static final String TAG = "ThemeAccentUtils";

    // Vendor overlays to ignore
    public static final String[] BLACKLIST_VENDOR_OVERLAYS = {
        "SysuiDarkTheme",
        "Pixel",
        "DisplayCutoutEmulationCorner",
        "DisplayCutoutEmulationDouble",
        "DisplayCutoutEmulationNarrow",
        "DisplayCutoutEmulationWide",
    };

    // Stock dark theme package
    private static final String STOCK_DARK_THEME = "com.android.systemui.theme.dark";

      // Light themes
      private static final String[] LIGHT_THEMES = {
        "com.google.intelligence.sense.theme.light", // 0
        "com.android.gboard.theme.light", // 1
    };

    // Dark themes
    private static final String[] DARK_THEMES = {
        "com.android.system.theme.dark", // 0
        "com.android.systemui.theme.custom.dark", // 1
        "com.android.settings.theme.dark", // 2
        "com.android.settings.intelligence.theme.dark", // 3
        "com.android.gboard.theme.dark", // 4
        "com.google.intelligence.sense.theme.dark", // 5
        "com.android.updater.theme.dark", // 6
        "com.android.wellbeing.theme.dark", // 7
    };

    // BlackAF themes
    private static final String[] BLACKAF_THEMES = {
        "com.android.system.theme.blackaf", // 0
        "com.android.systemui.theme.custom.blackaf", // 1
        "com.android.settings.theme.blackaf", // 2
        "com.android.settings.intelligence.theme.blackaf", // 3
        "com.android.gboard.theme.blackaf", // 4
        "com.google.intelligence.sense.theme.blackaf", // 5
        "com.android.updater.theme.blackaf", // 6
        "com.android.wellbeing.theme.blackaf", // 7
    };

    // Accents
    private static final String[] ACCENTS = {
        "default_accent", // 0
        "com.accents.red", // 1
        "com.accents.pink", // 2
        "com.accents.purple", // 3
        "com.accents.deeppurple", // 4
        "com.accents.indigo", // 5
        "com.accents.blue", // 6
        "com.accents.lightblue", // 7
        "com.accents.cyan", // 8
        "com.accents.teal", // 9
        "com.accents.green", // 10
        "com.accents.lightgreen", // 11
        "com.accents.lime", // 12
        "com.accents.yellow", // 13
        "com.accents.amber", // 14
        "com.accents.orange", // 15
        "com.accents.deeporange", // 16
        "com.accents.brown", // 17
        "com.accents.grey", // 18
        "com.accents.bluegrey", // 19
        "com.accents.black", // 20
        "com.accents.white", // 21
        "com.accents.qone", // 22
        "com.accents.qtwo", // 23
        "com.accents.qthree", // 24
        "com.accents.qfour", // 25
        "com.accents.qfive", // 26
        "com.accents.qsix", // 27
        "com.accents.brandone", // 28
        "com.accents.brandtwo", // 29
        "com.accents.brandthree", // 30
        "com.accents.brandfour", // 31
        "com.accents.brandfive", // 32
        "com.accents.brandsix", // 33
        "com.accents.brandseven", // 34
        "com.accents.brandeight", // 35
        "com.accents.brandnine", // 36
        "com.accents.brandten", // 37
        "com.accents.brandeleven", // 38
        "com.accents.brandtwelve", // 39
        "com.accents.brandthirteen", // 40
        "com.accents.brandfourteen", // 41
        "com.accents.brandfifteen", // 42
        "com.accents.brandsixteen", // 43
        "com.accents.brandseventeen", // 44
        "com.accents.brandeighteen", // 45
        "com.accents.brandnineteen", // 46
        "com.accents.brandtwenty", // 47
    };

    private static final String[] QS_TILE_THEMES = {
        "default_qstile", // 0
        "com.android.systemui.qstile.squircle", // 1
        "com.android.systemui.qstile.teardrop", // 2
        "com.android.systemui.qstile.deletround", // 3
        "com.android.systemui.qstile.inktober", // 4
        "com.android.systemui.qstile.shishunights", // 5
        "com.android.systemui.qstile.circlegradient", // 6
        "com.android.systemui.qstile.wavey", // 7
        "com.android.systemui.qstile.circledualtone", // 8
        "com.android.systemui.qstile.squaremedo", // 9
        "com.android.systemui.qstile.pokesign", // 10
        "com.android.systemui.qstile.ninja", // 11
        "com.android.systemui.qstile.dottedcircle", // 12
        "com.android.systemui.qstile.shishuink", // 13
        "com.android.systemui.qstile.attemptmountain", // 14
        "com.android.systemui.qstile.cookie", // 15
        "com.android.systemui.qstile.neonlike", // 16
        "com.android.systemui.qstile.oos", // 17
        "com.android.systemui.qstile.triangles", // 18
        "com.android.systemui.qstile.divided", // 19
        "com.android.systemui.qstile.cosmos" // 20
    };

    // Unloads the stock dark theme
    public static void unloadStockDarkTheme(IOverlayManager om, int userId) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo(STOCK_DARK_THEME,
                    userId);
            if (themeInfo != null && themeInfo.isEnabled()) {
                om.setEnabled(STOCK_DARK_THEME,
                        false /*disable*/, userId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Check for the dark system theme
    public static boolean isUsingDarkTheme(IOverlayManager om, int userId) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo(DARK_THEMES[0],
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }

    // Check for the blackaf system theme
    public static boolean isUsingBlackAFTheme(IOverlayManager om, int userId) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo(BLACKAF_THEMES[0],
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }

    // Set light / dark theme
    public static void setLightDarkTheme(IOverlayManager om, int userId, boolean useDarkTheme) {
        for (String theme : DARK_THEMES) {
            try {
                om.setEnabled(theme,
                        useDarkTheme, userId);
                if (useDarkTheme) {
                    unloadStockDarkTheme(om, userId);
                }
            } catch (RemoteException e) {
            }
        }
        for (String theme : LIGHT_THEMES) {
            try {
                om.setEnabled(theme,
                        !useDarkTheme, userId);
            } catch (RemoteException e) {
            }
        }
        unfuckBlackWhiteAccent(om, userId);
    }

    // Set black theme
    public static void setLightBlackAFTheme(IOverlayManager om, int userId, boolean useBlackAFTheme) {
        for (String theme : BLACKAF_THEMES) {
            try {
                om.setEnabled(theme,
                        useBlackAFTheme, userId);
                unfuckBlackWhiteAccent(om, userId);
                if (useBlackAFTheme) {
                    unloadStockDarkTheme(om, userId);
                }
            } catch (RemoteException e) {
            }
        }
    }

    // Check for black and white accent overlays
    public static void unfuckBlackWhiteAccent(IOverlayManager om, int userId) {
        OverlayInfo themeInfo = null;
        try {
            if (isUsingDarkTheme (om, userId) || isUsingBlackAFTheme (om, userId)) {
                themeInfo = om.getOverlayInfo(ACCENTS[20],
                        userId);
                if (themeInfo != null && themeInfo.isEnabled()) {
                    om.setEnabled(ACCENTS[20],
                            false /*disable*/, userId);
                    om.setEnabled(ACCENTS[21],
                            true, userId);
                }
            } else {
                themeInfo = om.getOverlayInfo(ACCENTS[21],
                        userId);
                if (themeInfo != null && themeInfo.isEnabled()) {
                    om.setEnabled(ACCENTS[21],
                            false /*disable*/, userId);
                    om.setEnabled(ACCENTS[20],
                            true, userId);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Check for any accent overlay
    public static boolean isUsingAccent(IOverlayManager om, int userId, int accent) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo(ACCENTS[accent],
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }

    // Switches theme accent from one to another or back to stock
    public static void updateAccents(IOverlayManager om, int userId, int accentSetting) {
        if (accentSetting == 0) {
            unloadAccents(om, userId);
        } else if (accentSetting < 20) {
            try {
                om.setEnabled(ACCENTS[accentSetting],
                        true, userId);
            } catch (RemoteException e) {
            }
        } else if (accentSetting > 21) {
            try {
                om.setEnabled(ACCENTS[accentSetting],
                        true, userId);
            } catch (RemoteException e) {
            }
        } else if (accentSetting == 20) {
            try {
                // If using a dark theme we use the white accent, otherwise use the black accent
                if (isUsingDarkTheme(om, userId) || isUsingBlackAFTheme(om, userId)) {
                    om.setEnabled(ACCENTS[21],
                            true, userId);
                } else {
                    om.setEnabled(ACCENTS[20],
                            true, userId);
                }
            } catch (RemoteException e) {
            }
        }
    }

    // Unload all the theme accents
    public static void unloadAccents(IOverlayManager om, int userId) {
        // skip index 0
        for (int i = 1; i < ACCENTS.length; i++) {
            String accent = ACCENTS[i];
            try {
                om.setEnabled(accent,
                        false /*disable*/, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Switches qs tile style to user selected.
    public static void updateTileStyle(IOverlayManager om, int userId, int qsTileStyle) {
        if (qsTileStyle == 0) {
            unlockQsTileStyles(om, userId);
        } else {
            try {
                om.setEnabled(QS_TILE_THEMES[qsTileStyle],
                        true, userId);
            } catch (RemoteException e) {
            }
        }
    }

    // Unload all the qs tile styles
    public static void unlockQsTileStyles(IOverlayManager om, int userId) {
        // skip index 0
        for (int i = 1; i < QS_TILE_THEMES.length; i++) {
            String qstiletheme = QS_TILE_THEMES[i];
            try {
                om.setEnabled(qstiletheme,
                        false /*disable*/, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Check for any QS tile styles overlay
    public static boolean isUsingQsTileStyles(IOverlayManager om, int userId, int qsstyle) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo(QS_TILE_THEMES[qsstyle],
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }
}
