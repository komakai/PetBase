package net.telepathix.petbase.helper

import android.view.View

fun View.setVisibility(visible: Boolean) = if (visible) this.setVisibility(View.VISIBLE) else this.setVisibility(View.GONE)
