package cn.zjx.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;


/**
 * Created by dcq on 2017/3/23.
 * <p>
 * StepView每个Item左侧的步骤节点绘制
 */

public class VisitStepNodeItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_DOT_STROKE_WIDTH = 2;
    private static final int DEFAULT_SPACE = 4;

    public static final int POSITION_TOP = 0;
    public static final int POSITION_CENTER = 1;

    private Builder mBuilder;
    private Paint mLinePaint;
    private Paint successPaint;
    private Paint failPaint;
    private Paint undoPaint;
    private int leftPadding;
    private int ringRadius, radius;
    private int space;

    private VisitStepNodeItemDecoration(Builder builder) {
        mBuilder = builder;
        initPaint();
    }

    private void initPaint() {
        leftPadding = mBuilder.leftMargin + mBuilder.rightMargin;

        radius = mBuilder.radius;
        ringRadius = radius + UiUtils.dip2px(mBuilder.context, DEFAULT_DOT_STROKE_WIDTH);
        space = UiUtils.dip2px(mBuilder.context, DEFAULT_SPACE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mBuilder.lineColor);
        mLinePaint.setStrokeWidth(mBuilder.lineWidth);

        successPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        successPaint.setStyle(Paint.Style.FILL);
        successPaint.setColor(mBuilder.successColor);

        failPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        failPaint.setStyle(Paint.Style.FILL);
        failPaint.setColor(mBuilder.failColor);

        undoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        undoPaint.setStyle(Paint.Style.FILL);
        undoPaint.setColor(mBuilder.undoColor);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.save();
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // 起始x位置
            int sx = child.getLeft() - params.leftMargin - mBuilder.rightMargin;
            // 起始y位置
            int sy = child.getTop() - space;
            int dy = child.getBottom() - params.bottomMargin;


            int cy = dy - child.getHeight() / 2;
            int maxCy = radius;
            if (mBuilder.dotPosition == POSITION_TOP) {
                cy = dy - child.getHeight() / 3 * 2;
            }

            // 获取当前child在Adapter中的position
            int adapterPosition = parent.getChildAdapterPosition(child);
            // 绘制节点圆圈
            int type = mBuilder.itemDatas.get(i).type;
            System.out.println("-----------------" + mBuilder.itemDatas.get(i).type);
            if (mBuilder.itemDatas.get(i).type == 1) {
                // 使用设置的drawable资源作为圆点
                if (mBuilder.failDrawable != null) {
                    int left = mBuilder.leftMargin - mBuilder.failDrawable.getIntrinsicWidth() / 2;
                    int top = cy - mBuilder.failDrawable.getIntrinsicHeight() / 2;
                    int right = left + mBuilder.failDrawable.getIntrinsicWidth();
                    int bottom = top + mBuilder.failDrawable.getIntrinsicHeight();
                    mBuilder.failDrawable.setBounds(left, top, right, bottom);
                    mBuilder.failDrawable.draw(c);
                    if (adapterPosition == 0) {
                        maxCy = mBuilder.failDrawable.getIntrinsicHeight() / 2;
                    }
                } else {
                    c.drawCircle(sx, cy, radius, failPaint);
                    maxCy = ringRadius;
                }
            } else if (mBuilder.itemDatas.get(i).type == 2) {
                if (mBuilder.undoDrawable != null) {
                    int left = mBuilder.leftMargin - mBuilder.undoDrawable.getIntrinsicWidth() / 2;
                    int top = cy - mBuilder.undoDrawable.getIntrinsicHeight() / 2;
                    int right = left + mBuilder.undoDrawable.getIntrinsicWidth();
                    int bottom = top + mBuilder.undoDrawable.getIntrinsicHeight();
                    mBuilder.undoDrawable.setBounds(left, top, right, bottom);
                    mBuilder.undoDrawable.draw(c);
                } else {
                    c.drawCircle(sx, cy, radius, undoPaint);
                    maxCy = ringRadius;
                }
            } else if (mBuilder.itemDatas.get(i).type == 3) {
                if (mBuilder.successDrawable != null) {
                    maxCy = mBuilder.successDrawable.getIntrinsicHeight() / 2;
                    int left = mBuilder.leftMargin - mBuilder.successDrawable.getIntrinsicWidth() / 2;
                    int top = cy - mBuilder.successDrawable.getIntrinsicHeight() / 2;
                    int right = left + mBuilder.successDrawable.getIntrinsicWidth();
                    int bottom = top + mBuilder.successDrawable.getIntrinsicHeight();
                    mBuilder.successDrawable.setBounds(left, top, right, bottom);
                    mBuilder.successDrawable.draw(c);
                } else {
                    c.drawCircle(sx, cy, radius, successPaint);
                    maxCy = radius;
                }
            }


            // 绘制上竖线
            if (adapterPosition > 0) {
                c.drawLine(sx, sy, sx, cy - maxCy - 0, mLinePaint);
            }

            // 绘制下竖线
            if (adapterPosition < parent.getAdapter().getItemCount() - 1) {
                c.drawLine(sx, cy + maxCy + 0, sx, dy, mLinePaint);
            }
        }
        c.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(leftPadding, 0, 0, 0);
    }

    public static class Builder {

        private int leftMargin, rightMargin;
        private int lineColor;
        private int lineWidth;
        private int successColor;
        private int failColor;
        private int undoColor;
        private int dotPosition;
        private Drawable successDrawable;
        private Drawable failDrawable;
        private Drawable undoDrawable;
        private int radius;
        private Context context;
        private List<VisitStepModel> itemDatas;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setSuccessDrawable(Drawable successDrawable) {
            this.successDrawable = successDrawable;
            return this;
        }

        public Builder setFailDrawable(Drawable failDrawable) {
            this.failDrawable = failDrawable;
            return this;
        }

        public Builder setUndoDrawable(Drawable undoDrawable) {
            this.undoDrawable = undoDrawable;
            return this;
        }

        public Builder setDotPosition(int dotPosition) {
            this.dotPosition = dotPosition;
            return this;
        }

        public Builder setData(List<VisitStepModel> itemDatas) {
            this.itemDatas = itemDatas;
            return this;
        }

        public Builder setSuccessColor(int successColor) {
            this.successColor = successColor;
            return this;
        }

        public Builder setFailColor(int failColor) {
            this.failColor = failColor;
            return this;
        }

        public Builder setUndoColor(int undoColor) {
            this.undoColor = undoColor;
            return this;
        }

        public Builder setLineWidth(int lineWidth) {
            this.lineWidth = lineWidth;
            return this;
        }

        public Builder setLeftMargin(int leftMargin) {
            this.leftMargin = leftMargin;
            return this;
        }

        public Builder setRightMargin(int rightMargin) {
            this.rightMargin = rightMargin;
            return this;
        }

        public Builder setLineColor(int lineColor) {
            this.lineColor = lineColor;
            return this;
        }

        public VisitStepNodeItemDecoration build() {
            return new VisitStepNodeItemDecoration(this);
        }
    }
}
