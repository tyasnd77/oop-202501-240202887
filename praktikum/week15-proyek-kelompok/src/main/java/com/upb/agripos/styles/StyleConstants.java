/*
 * INLINE STYLES REFERENCE FOR AGRI-POS
 * 
 * File ini berisi collection dari inline style strings
 * yang dapat digunakan langsung di JavaFX components.
 * 
 * Gunakan dengan: component.setStyle(StyleConstants.STYLE_NAME);
 */

package com.upb.agripos.styles;

/**
 * Kumpulan inline CSS styles untuk JavaFX Agri-POS
 * 
 * Warna:
 * - Primary Dark: #14532d
 * - Primary Medium: #15803d
 * - Primary Light: #16a34a
 * - Secondary: #059669, #047857
 * - Success: #10b981
 * - Warning: #f59e0b
 * - Danger: #ef4444
 * - Info: #3b82f6
 */
public class StyleConstants {
    
    // ================================================
    // WARNA DASAR
    // ================================================
    public static final String COLOR_PRIMARY_DARK = "#14532d";
    public static final String COLOR_PRIMARY_MEDIUM = "#15803d";
    public static final String COLOR_PRIMARY_LIGHT = "#16a34a";
    public static final String COLOR_SECONDARY_DARK = "#047857";
    public static final String COLOR_SECONDARY_MEDIUM = "#059669";
    public static final String COLOR_SECONDARY_LIGHT = "#6ee7b7";
    
    public static final String COLOR_SUCCESS = "#10b981";
    public static final String COLOR_WARNING = "#f59e0b";
    public static final String COLOR_DANGER = "#ef4444";
    public static final String COLOR_INFO = "#3b82f6";
    
    public static final String COLOR_WHITE = "#ffffff";
    public static final String COLOR_GRAY_100 = "#f3f4f6";
    public static final String COLOR_GRAY_200 = "#e5e7eb";
    public static final String COLOR_GRAY_300 = "#d1d5db";
    public static final String COLOR_GRAY_600 = "#4b5563";
    public static final String COLOR_GRAY_700 = "#374151";
    
    // ================================================
    // HEADER STYLES
    // ================================================
    public static final String HEADER_CONTAINER = 
        "-fx-background-color: linear-gradient(to right, #14532d, #15803d);" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 15px 20px;" +
        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 10, 0, 0, 0);";
    
    public static final String HEADER_LABEL = 
        "-fx-text-fill: #ffffff;" +
        "-fx-font-weight: bold;" +
        "-fx-font-size: 16px;";
    
    public static final String HEADER_LOGO = 
        "-fx-font-size: 20px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #ffffff;";
    
    public static final String BUTTON_LOGOUT = 
        "-fx-background-color: rgba(239, 68, 68, 0.2);" +
        "-fx-text-fill: #ffffff;" +
        "-fx-border-color: rgba(239, 68, 68, 0.5);" +
        "-fx-border-radius: 4;" +
        "-fx-padding: 8px 16px;" +
        "-fx-font-size: 12px;" +
        "-fx-font-weight: bold;";
    
    // ================================================
    // BUTTON STYLES
    // ================================================
    public static final String BUTTON_PRIMARY = 
        "-fx-background-color: linear-gradient(to right, #16a34a, #059669);" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 12px 24px;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-border-radius: 5;" +
        "-fx-background-radius: 5;" +
        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 5, 0, 0, 0);";
    
    public static final String BUTTON_SECONDARY = 
        "-fx-background-color: #e5e7eb;" +
        "-fx-text-fill: #374151;" +
        "-fx-padding: 10px 20px;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-border-radius: 5;";
    
    public static final String BUTTON_SUCCESS = 
        "-fx-background-color: #10b981;" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 10px 20px;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-border-radius: 5;";
    
    public static final String BUTTON_DANGER = 
        "-fx-background-color: #ef4444;" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 10px 20px;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-border-radius: 5;";
    
    public static final String BUTTON_WARNING = 
        "-fx-background-color: #f59e0b;" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 10px 20px;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-border-radius: 5;";
    
    public static final String BUTTON_INFO = 
        "-fx-background-color: #3b82f6;" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 10px 20px;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-border-radius: 5;";
    
    public static final String BUTTON_SMALL = 
        "-fx-padding: 6px 12px;" +
        "-fx-font-size: 11px;" +
        "-fx-border-radius: 4;" +
        "-fx-background-radius: 4;";
    
    // ================================================
    // INPUT FIELD STYLES
    // ================================================
    public static final String TEXTFIELD_DEFAULT = 
        "-fx-padding: 10px;" +
        "-fx-border-color: #d1d5db;" +
        "-fx-border-radius: 5;" +
        "-fx-background-radius: 5;" +
        "-fx-control-inner-background: #ffffff;" +
        "-fx-font-size: 12px;" +
        "-fx-text-fill: #374151;";
    
    public static final String TEXTFIELD_FOCUS = 
        "-fx-border-color: #16a34a;" +
        "-fx-border-width: 2;" +
        "-fx-effect: dropshadow(gaussian, rgba(22, 163, 74, 0.2), 5, 0, 0, 0);";
    
    public static final String TEXTFIELD_ERROR = 
        "-fx-border-color: #ef4444;" +
        "-fx-border-width: 2;";
    
    // ================================================
    // CARD STYLES
    // ================================================
    public static final String CARD_DEFAULT = 
        "-fx-background-color: #ffffff;" +
        "-fx-border-color: #d1d5db;" +
        "-fx-border-radius: 8;" +
        "-fx-border-width: 1;" +
        "-fx-padding: 20px;" +
        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 5, 0, 0, 0);";
    
    public static final String CARD_ELEVATED = 
        "-fx-background-color: #ffffff;" +
        "-fx-border-color: #16a34a;" +
        "-fx-border-radius: 8;" +
        "-fx-border-width: 2;" +
        "-fx-padding: 20px;" +
        "-fx-effect: dropshadow(gaussian, rgba(22, 163, 74, 0.15), 10, 0, 2, 2);";
    
    public static final String STAT_CARD = 
        "-fx-background-color: linear-gradient(to bottom right, #16a34a, #059669);" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 25px;" +
        "-fx-border-radius: 8;" +
        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 8, 0, 0, 0);";
    
    // ================================================
    // LABEL STYLES
    // ================================================
    public static final String LABEL_TITLE = 
        "-fx-font-size: 24px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #14532d;";
    
    public static final String LABEL_SUBTITLE = 
        "-fx-font-size: 14px;" +
        "-fx-text-fill: #6b7280;";
    
    public static final String LABEL_HEADER = 
        "-fx-font-size: 16px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #16a34a;";
    
    public static final String LABEL_FORM = 
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #14532d;" +
        "-fx-font-size: 12px;";
    
    public static final String LABEL_ERROR = 
        "-fx-text-fill: #ef4444;" +
        "-fx-font-size: 10px;";
    
    public static final String LABEL_SUCCESS = 
        "-fx-text-fill: #10b981;" +
        "-fx-font-size: 10px;";
    
    public static final String LABEL_WARNING = 
        "-fx-text-fill: #f59e0b;" +
        "-fx-font-size: 10px;";
    
    // ================================================
    // TABLE STYLES
    // ================================================
    public static final String TABLE_HEADER = 
        "-fx-background-color: linear-gradient(to right, #16a34a, #059669);" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 12px;" +
        "-fx-font-weight: bold;" +
        "-fx-font-size: 13px;";
    
    public static final String TABLE_ROW = 
        "-fx-padding: 8px;" +
        "-fx-border-color: #f3f4f6;" +
        "-fx-border-width: 0 0 1 0;" +
        "-fx-background-color: #ffffff;";
    
    public static final String TABLE_ROW_ODD = 
        "-fx-background-color: #f0fdf4;";
    
    public static final String TABLE_CONTAINER = 
        "-fx-background-color: #ffffff;" +
        "-fx-border-color: #d1d5db;" +
        "-fx-border-radius: 5;" +
        "-fx-border-width: 1;" +
        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 5, 0, 0, 0);";
    
    // ================================================
    // PRODUCT CARD STYLES
    // ================================================
    public static final String PRODUCT_CARD = 
        "-fx-background-color: #ffffff;" +
        "-fx-border-color: #d1d5db;" +
        "-fx-border-radius: 8;" +
        "-fx-border-width: 1;" +
        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 5, 0, 0, 0);";
    
    public static final String PRODUCT_NAME = 
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #374151;" +
        "-fx-font-size: 12px;";
    
    public static final String PRODUCT_PRICE = 
        "-fx-font-size: 16px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #16a34a;";
    
    public static final String PRODUCT_CATEGORY = 
        "-fx-font-size: 11px;" +
        "-fx-text-fill: #6b7280;" +
        "-fx-font-weight: bold;";
    
    public static final String BADGE_STOK_READY = 
        "-fx-background-color: rgba(16, 185, 129, 0.1);" +
        "-fx-text-fill: #10b981;" +
        "-fx-padding: 4px 8px;" +
        "-fx-border-color: #10b981;" +
        "-fx-border-radius: 3;" +
        "-fx-border-width: 1;" +
        "-fx-font-size: 10px;" +
        "-fx-font-weight: bold;";
    
    public static final String BADGE_STOK_LOW = 
        "-fx-background-color: rgba(245, 158, 11, 0.1);" +
        "-fx-text-fill: #f59e0b;" +
        "-fx-padding: 4px 8px;" +
        "-fx-border-color: #f59e0b;" +
        "-fx-border-radius: 3;" +
        "-fx-border-width: 1;" +
        "-fx-font-size: 10px;" +
        "-fx-font-weight: bold;";
    
    public static final String BADGE_STOK_OUT = 
        "-fx-background-color: rgba(239, 68, 68, 0.1);" +
        "-fx-text-fill: #ef4444;" +
        "-fx-padding: 4px 8px;" +
        "-fx-border-color: #ef4444;" +
        "-fx-border-radius: 3;" +
        "-fx-border-width: 1;" +
        "-fx-font-size: 10px;" +
        "-fx-font-weight: bold;";
    
    // ================================================
    // CART STYLES
    // ================================================
    public static final String CART_SIDEBAR = 
        "-fx-background-color: #ffffff;" +
        "-fx-border-left: 1 solid #d1d5db;";
    
    public static final String CART_HEADER = 
        "-fx-background-color: linear-gradient(to right, #16a34a, #059669);" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 20px;" +
        "-fx-border-bottom: 1 solid #d1d5db;";
    
    public static final String CART_ITEM = 
        "-fx-padding: 12px;" +
        "-fx-border-color: #d1d5db;" +
        "-fx-border-radius: 5;" +
        "-fx-background-color: #f0fdf4;";
    
    public static final String CART_TOTAL_LABEL = 
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #374151;" +
        "-fx-font-size: 12px;";
    
    public static final String CART_TOTAL_AMOUNT = 
        "-fx-font-size: 20px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: #16a34a;";
    
    public static final String BUTTON_CHECKOUT = 
        "-fx-background-color: linear-gradient(to right, #16a34a, #059669);" +
        "-fx-text-fill: #ffffff;" +
        "-fx-padding: 16px;" +
        "-fx-font-size: 16px;" +
        "-fx-font-weight: bold;" +
        "-fx-border-radius: 5;" +
        "-fx-background-radius: 5;" +
        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 5, 0, 0, 0);";
    
    // ================================================
    // QUANTITY CONTROL STYLES
    // ================================================
    public static final String QUANTITY_BUTTON = 
        "-fx-padding: 6px 10px;" +
        "-fx-background-color: transparent;" +
        "-fx-text-fill: #16a34a;" +
        "-fx-font-weight: bold;" +
        "-fx-border-radius: 3;" +
        "-fx-font-size: 12px;";
    
    public static final String QUANTITY_DISPLAY = 
        "-fx-padding: 6px 10px;" +
        "-fx-text-fill: #374151;" +
        "-fx-font-weight: bold;" +
        "-fx-font-size: 12px;";
    
    // ================================================
    // CONTAINER STYLES
    // ================================================
    public static final String CONTAINER = 
        "-fx-background-color: #f9fafb;" +
        "-fx-padding: 20px;";
    
    public static final String CONTAINER_LIGHT = 
        "-fx-background-color: #ffffff;" +
        "-fx-padding: 20px;";
    
    public static final String HBOX_SPACING = 
        "-fx-spacing: 15px;" +
        "-fx-padding: 15px;";
    
    public static final String VBOX_SPACING = 
        "-fx-spacing: 15px;" +
        "-fx-padding: 15px;";
    
    // ================================================
    // ALERT STYLES
    // ================================================
    public static final String ALERT_SUCCESS = 
        "-fx-background-color: rgba(16, 185, 129, 0.1);" +
        "-fx-text-fill: #10b981;" +
        "-fx-padding: 12px;" +
        "-fx-border-color: #10b981;" +
        "-fx-border-left-width: 4;" +
        "-fx-border-radius: 4;";
    
    public static final String ALERT_ERROR = 
        "-fx-background-color: rgba(239, 68, 68, 0.1);" +
        "-fx-text-fill: #ef4444;" +
        "-fx-padding: 12px;" +
        "-fx-border-color: #ef4444;" +
        "-fx-border-left-width: 4;" +
        "-fx-border-radius: 4;";
    
    public static final String ALERT_WARNING = 
        "-fx-background-color: rgba(245, 158, 11, 0.1);" +
        "-fx-text-fill: #f59e0b;" +
        "-fx-padding: 12px;" +
        "-fx-border-color: #f59e0b;" +
        "-fx-border-left-width: 4;" +
        "-fx-border-radius: 4;";
    
    public static final String ALERT_INFO = 
        "-fx-background-color: rgba(59, 130, 246, 0.1);" +
        "-fx-text-fill: #3b82f6;" +
        "-fx-padding: 12px;" +
        "-fx-border-color: #3b82f6;" +
        "-fx-border-left-width: 4;" +
        "-fx-border-radius: 4;";
    
    // ================================================
    // TAB STYLES
    // ================================================
    public static final String TAB_PANE = 
        "-fx-background-color: #ffffff;" +
        "-fx-padding: 0;";
    
    public static final String TAB_INACTIVE = 
        "-fx-background-color: #ffffff;" +
        "-fx-text-fill: #6b7280;" +
        "-fx-padding: 12px 20px;" +
        "-fx-font-weight: bold;";
    
    public static final String TAB_ACTIVE = 
        "-fx-background-color: #ffffff;" +
        "-fx-text-fill: #16a34a;" +
        "-fx-padding: 12px 20px;" +
        "-fx-font-weight: bold;" +
        "-fx-border-bottom: 3 solid #16a34a;";
    
    // ================================================
    // SEPARATOR STYLES
    // ================================================
    public static final String SEPARATOR = 
        "-fx-background-color: #d1d5db;";
    
    // ================================================
    // UTILITY METHODS
    // ================================================
    
    /**
     * Combine multiple styles
     */
    public static String combine(String... styles) {
        return String.join(";", styles);
    }
    
    /**
     * Apply color to background
     */
    public static String bgColor(String color) {
        return "-fx-background-color: " + color + ";";
    }
    
    /**
     * Apply color to text
     */
    public static String textColor(String color) {
        return "-fx-text-fill: " + color + ";";
    }
    
    /**
     * Apply padding
     */
    public static String padding(int all) {
        return "-fx-padding: " + all + ";";
    }
    
    public static String padding(int vertical, int horizontal) {
        return "-fx-padding: " + vertical + " " + horizontal + ";";
    }
    
    public static String padding(int top, int right, int bottom, int left) {
        return "-fx-padding: " + top + " " + right + " " + bottom + " " + left + ";";
    }
    
    /**
     * Apply border radius
     */
    public static String borderRadius(int radius) {
        return "-fx-border-radius: " + radius + ";";
    }
    
    /**
     * Apply font size
     */
    public static String fontSize(int size) {
        return "-fx-font-size: " + size + ";";
    }
    
    /**
     * Apply gradient
     */
    public static String gradient(String color1, String color2) {
        return "-fx-background-color: linear-gradient(to right, " + color1 + ", " + color2 + ");";
    }
    
    /**
     * Apply shadow effect
     */
    public static String shadow(int blur, double offsetX, double offsetY) {
        return "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), " + 
               blur + ", 0, " + offsetX + ", " + offsetY + ");";
    }
}
