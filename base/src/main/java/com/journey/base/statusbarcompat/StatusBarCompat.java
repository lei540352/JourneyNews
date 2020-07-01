package com.journey.base.statusbarcompat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.journey.base.utils.Logger;

import io.reactivex.annotations.NonNull;

/**
 * Utils for status bar
 * Created by qiu on 3/29/16.
 */
public class StatusBarCompat {

    //Get alpha color获取Alpha颜色
    static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * set statusBarColor
     *设置状态栏颜色
     * @param statusColor color
     * @param alpha       0 - 255
     */
    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, int alpha) {
        setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha), false);
    }

    public static void setStatusBarColor(@NonNull Activity activity, @ColorInt int statusColor, boolean isNight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColor(activity, statusColor, isNight);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColor(activity, statusColor);
        }
    }

    /**
     * 设置statusbar为渐变色背景
     *
     * @param activity
     * @param drawableResource 渐变背景drawableResource
     */
    public static void setStatusBarGradientColor(@NonNull Activity activity, int drawableResource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarGradientColor(activity, drawableResource, false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarGradientColor(activity, drawableResource);
        }
    }

    /**
     * 半透明状态栏
     * @param activity
     */
    public static void translucentStatusBar(@NonNull Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * 设置statusbar头部
     * textBlack 状态栏文字是黑色的么  false 白 true 黑
     * 当SDK> 21时隐藏状态栏alpha背景，如果隐藏则为true
     * @param hideStatusBarBg hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBg, boolean textBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBarForImage(activity, hideStatusBarBg, textBlack);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBarForImage(activity);
        }
    }

    /**
     * change to full screen mode
     *更改为全屏模式
     * hideStatusBarBg隐藏状态栏alpha当SDK> 21时为背景，如果隐藏则为true
     * @param hideStatusBarBg hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBg);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity);
        }
    }

    /**
     * @param activity
     * @param hideStatusBarBg statusBar 是否透明
     * @param bgView                  需要沉浸效果的view
     */
    public static void translucentStatusBar(@NonNull Activity activity, boolean hideStatusBarBg, View bgView, boolean textBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBg, bgView, textBlack);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBar(activity, bgView);
        }
    }
    //片段的半透明状态栏
    public static void translucentStatusBarForFragment(@NonNull Fragment fragment, boolean hideStatusBarBg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBarForFragment(fragment, hideStatusBarBg);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBarForFragment(fragment);
        }
    }

    /**
     * @param fragment
     * @param hideStatusBarBg statusBar 是否透明
     * @param bgView                  需要沉浸效果的view
     */
    public static void translucentStatusBarForFragment(@NonNull Fragment fragment, boolean hideStatusBarBg, View bgView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.translucentStatusBarForFragment(fragment, hideStatusBarBg, bgView);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.translucentStatusBarForFragment(fragment, bgView);
        }
    }

    //设置状态栏颜色以折叠工具栏
    public static void setStatusBarColorForCollapsingToolbar(@NonNull Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
                                                             Toolbar toolbar, @ColorInt int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarCompatLollipop.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompatKitKat.setStatusBarColorForCollapsingToolbar(activity, appBarLayout, collapsingToolbarLayout, toolbar, statusColor);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    /** 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)*/
    public static void setPaddingSmart(Context context, View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null && lp.height > 0) {
                lp.height += getStatusBarHeight(context);//增高
            }
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }


    //发现颜色好亮啊，状态栏里的文字都看不清了。在这种情况下，我们是可以将状态栏文字的颜色改成深色的，官方也仅支持设置状态栏
    public static void setTextDark(Context context, boolean isDark) {
        if (context instanceof Activity) {
            setTextDark(((Activity) context).getWindow(), isDark);
        }
    }
    private static void setTextDark(Window window, boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (isDark) {
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 颜色是否是深色的
     * @param color
     * @return
     */
    public static boolean isDarkColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) < 0.5;
    }

    //可以将状态栏文字的颜色改成需要的颜色
    public static void setColor(@NonNull Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(color);
            setTextDark(window, !isDarkColor(color));
        }
    }

    public static int generateBitmapYAverage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight(); // 保存所有的像素的数组，图片宽×高
        int[] pixels = new int[width * height];

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int totalY = 0;
        int l = pixels.length;
        if(l >100){
             l = 100;
        }
        for (int i = 0; i < l; i++) {
            int clr = pixels[i];
            double darkness = 1 - (0.299 * Color.red(clr) + 0.587 * Color.green(clr) + 0.114 * Color.blue(clr)) / 255;
            if (darkness < 0.5) {
                totalY += -1;
                Logger.d("tag", "它是浅色"); // It's a light color
            } else {
                Logger.d("tag", "它是深色"); // It's a dark color
                totalY += 1;
            }
        }
        return totalY;
    }
}
