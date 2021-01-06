package dk.au671048.e6prj02.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity
public class Route {
    @PrimaryKey
    @NonNull
    private String _id;
    private List<String> bins;
    private String assignee;

    public Route(@NotNull String _id, List<String> bins, String assignee) {
        this._id = _id;
        this.bins = bins;
        this.assignee = assignee;
    }

    @NotNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NotNull String _id) {
        this._id = _id;
    }

    public List<String> getBins() {
        return bins;
    }

    public void setBins(List<String> bins) {
        this.bins = bins;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
