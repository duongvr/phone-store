package org.acme; // Thay bằng package của bạn

import io.quarkus.qute.TemplateExtension;
import java.text.NumberFormat;
import java.util.Locale;

@TemplateExtension
public class FormatExtensions {

    // Định nghĩa hàm formatPrice cho Qute
    public static String formatPrice(Double price) {
        if (price == null) return "0 ₫";
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(price);
    }
}