package net.test.unittesting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.test.unittesting.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class ReposRvAdapter extends RecyclerView.Adapter<ReposRvAdapter.RepoViewHolder> {
    List<SearchResult> results = new ArrayList<>();

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RepoViewHolder(inflater.inflate(R.layout.rv_item_repo, null));
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder,
                                 int position) {
        holder.bind(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    void updateResults(List<SearchResult> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRepoName;

        RepoViewHolder(View itemView) {
            super(itemView);

            tvRepoName = itemView.findViewById(R.id.tv_repo_name);
        }

        void bind(SearchResult searchResult) {
            tvRepoName.setText(searchResult.getFullName());
        }
    }
}
