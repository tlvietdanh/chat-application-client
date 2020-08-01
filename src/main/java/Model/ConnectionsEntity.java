package Model;

import javax.persistence.*;

@Entity
@Table(name = "connections", schema = "1612083_chatapplication", catalog = "")
public class ConnectionsEntity {
    private int connectionId;
    private int requestId;
    private int receiveId;
    private String status;
    private String timestamp;

    @Id
    @Column(name = "connectionID")
    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    @Basic
    @Column(name = "requestID")
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    @Basic
    @Column(name = "receiveID")
    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectionsEntity that = (ConnectionsEntity) o;

        if (connectionId != that.connectionId) return false;
        if (requestId != that.requestId) return false;
        if (receiveId != that.receiveId) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = connectionId;
        result = 31 * result + requestId;
        result = 31 * result + receiveId;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
