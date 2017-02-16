/*
 * Copyright (c) 2014, 青岛司通科技有限公司 All rights reserved.
 * File Name：LoadingDialog.java
 * Version：V1.0
 * Author：zhaokaiqiang
 * Date：2014-10-24
 */

package nah.prayer.anbada.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import nah.prayer.anbada.R;


public class LoadingDialog extends Dialog {

	private ImageView image;

	public LoadingDialog(Context context) {
		super(context, R.style.Loading);
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_loading);
		image = (ImageView)findViewById(R.id.img_Loading_Dialog);
		image.setImageDrawable(new CircleLoadingDrawable(getContext()));
	}
}