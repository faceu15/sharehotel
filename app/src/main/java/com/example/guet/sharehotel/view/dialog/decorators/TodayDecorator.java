package com.example.guet.sharehotel.view.dialog.decorators;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.example.guet.sharehotel.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Created by HaiyuKing
 * Used 当天日期特殊显示
 */

public class TodayDecorator implements DayViewDecorator {

	private final CalendarDay today;
	private ForegroundColorSpan span;//文本颜色

	public TodayDecorator(Context mContext) {
		today = CalendarDay.today();
		span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.materialcalendar_today_border_color));
	}

	@Override
	public boolean shouldDecorate(CalendarDay day) {
		return today.equals(day);
	}

	@Override
	public void decorate(DayViewFacade view) {
		view.addSpan(span);
		view.addSpan(new StyleSpan(Typeface.BOLD));
		view.addSpan(new RelativeSizeSpan(1.4f));
	}

}
