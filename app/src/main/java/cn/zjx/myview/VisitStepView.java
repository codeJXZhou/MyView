package cn.zjx.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcq on 2017/3/22.[
 * <p>
 * 使用RecyclerView加载并展示每个记录，使用ItemDecoration装饰item，显示节点
 */

public class VisitStepView extends RecyclerView {

    public static final int DEFAULT_LEFT_MARGIN = 30;
    public static final int DEFAULT_RIGHT_MARGIN = 30;
    public static final int DEFAULT_DOT_RADIUS = 6;
    private static final int DEFAULT_LINE_WIDTH = 1;

    private int leftMargin, rightMargin;
    private int lineColor, lineWidth;
    private int successColor, failColor,undoColor;
    private int dotPosition;
    private int radius;
    private Drawable successDrawable;
    private Drawable failDrawable;
    private Drawable undoDrawable;
    private int defaultColor = Color.parseColor("#eeeeee");

    private List<VisitStepModel> itemDatas = new ArrayList<>();
    private StepAdapter mAdapter;
    private BindViewListener mListener;

    public VisitStepView(Context context) {
        this(context, null);
    }

    public VisitStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisitStepView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        leftMargin = UiUtils.dip2px(context, DEFAULT_LEFT_MARGIN);
        rightMargin = UiUtils.dip2px(context, DEFAULT_RIGHT_MARGIN);
        lineWidth = UiUtils.dip2px(context, DEFAULT_LINE_WIDTH);
        radius = UiUtils.dip2px(context, DEFAULT_DOT_RADIUS);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VisitStepView, defStyle, 0);

        leftMargin = (int) ta.getDimension(R.styleable.VisitStepView_leftMargin, leftMargin);
        rightMargin = (int) ta.getDimension(R.styleable.VisitStepView_rightMargin, rightMargin);
        lineWidth = (int) ta.getDimension(R.styleable.VisitStepView_lineWidth, lineWidth);
        radius = (int) ta.getDimension(R.styleable.VisitStepView_radius, radius);
        lineColor = ta.getColor(R.styleable.VisitStepView_lineColor, defaultColor);
        successColor = ta.getColor(R.styleable.VisitStepView_successColor, Color.parseColor("#d0d0d0"));
        failColor = ta.getColor(R.styleable.VisitStepView_failColor, Color.parseColor("#1c980f"));
        undoColor = ta.getColor(R.styleable.VisitStepView_undoColor, Color.parseColor("#1c980f"));
        dotPosition = ta.getInteger(R.styleable.VisitStepView_dotPosition, VisitStepNodeItemDecoration.POSITION_TOP);
        successDrawable = ta.getDrawable(R.styleable.VisitStepView_successDrawable);
        failDrawable = ta.getDrawable(R.styleable.VisitStepView_failDrawable);
        undoDrawable = ta.getDrawable(R.styleable.VisitStepView_undoDrawable);

        ta.recycle();

        init();
    }

    private void init() {
        mAdapter = new StepAdapter();
        setHasFixedSize(true);
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(mAdapter);


        // 添加左侧节点item装饰
        addItemDecoration(
                new VisitStepNodeItemDecoration.Builder(getContext())
                        .setLineColor(lineColor)
                        .setLeftMargin(leftMargin)
                        .setRightMargin(rightMargin)
                        .setSuccessColor(successColor)
                        .setFailColor(failColor)
                        .setUndoColor(undoColor)
                        .setLineWidth(lineWidth)
                        .setRadius(radius)
                        .setSuccessDrawable(successDrawable)
                        .setFailDrawable(failDrawable)
                        .setUndoDrawable(undoDrawable)
                        .setDotPosition(dotPosition)
                        .setData(itemDatas)
                        .build()
        );
    }

    @SuppressWarnings("unchecked")
    public void setDatas(List datas) {
        itemDatas.clear();
        itemDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    class StepViewHolder extends ViewHolder {

        TextView itemMsg;
        TextView itemDate;

        StepViewHolder(View itemView) {
            super(itemView);
            itemMsg = (TextView) itemView.findViewById(R.id.itemMsg);
            itemDate = (TextView) itemView.findViewById(R.id.itemDate);
        }
    }

    private class StepAdapter extends Adapter<StepViewHolder> {

        @Override
        public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logistics_step_view, parent, false);
            return new StepViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(StepViewHolder holder, int position) {
            VisitStepModel data = itemDatas.get(position);
            if (mListener != null) {
                mListener.onBindView(holder.itemMsg, holder.itemDate, data);
            }
        }

        @Override
        public int getItemCount() {
            return itemDatas.size();
        }
    }

    public static interface BindViewListener {
        void onBindView(TextView itemMsg, TextView itemDate, VisitStepModel data);
    }

    public void setBindViewListener(BindViewListener listener) {
        this.mListener = listener;
    }
}
