package cn.com.hkvideo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.hk_demo.Config;
import cn.com.hk_demo.R;

/**
 * ${function}
 *
 * @author Create by Yin Luo Fei
 * @version 0.1
 * @Time 2019/8/20 09 : 12
 */
public class VideoRecycler extends RecyclerView.Adapter<VideoRecycler.ViewHolder> {

    List<String> videoText;
    Context context;

    public VideoRecycler(Context context, List<String> videoText) {
        this.videoText = videoText;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.surface_tenplate, viewGroup, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.surfaceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                //点击监听
                Config.putInt("channel",pos);
                //点击后跳转到播放页\
                viewGroup.getContext().startActivity(new Intent(context,SimpleActivity.class));

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String str = videoText.get(i);

        viewHolder.surfaceText.setText(str);

    }

    @Override
    public int getItemCount() {
        return videoText.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView surfaceText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            surfaceText = itemView.findViewById(R.id.video_name);
        }

    }

}
