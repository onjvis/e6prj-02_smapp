package dk.au671048.e6prj02.app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity
public class Warning {
    @PrimaryKey
    @NonNull
    private String _id;
    private String binId;
    private String type;
    private Date timestamp;
    private String message;

    public Warning(@NotNull String _id, String binId, String type, Date timestamp, String message) {
        this._id = _id;
        this.binId = binId;
        this.type = type;
        this.timestamp = timestamp;
        this.message = message;
    }

    @NotNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NotNull String _id) {
        this._id = _id;
    }

    public String getBinId() {
        return binId;
    }

    public void setBinId(String binID) {
        this.binId = binID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
