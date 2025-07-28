package com.jitsi.jitsi_backend.Service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CallService {

    public enum CallStatus {
        STARTED, ACCEPTED, ENDED
    }

    public static class CallInfo {
        public String callerId;
        public String calleeId;
        public String roomId;
        public CallStatus status;

        public CallInfo(String callerId, String calleeId, String roomId, CallStatus status) {
            this.callerId = callerId;
            this.calleeId = calleeId;
            this.roomId = roomId;
            this.status = status;
        }
    }

    private final Map<String, CallInfo> activeCalls = new ConcurrentHashMap<>();

    public void startCall(String callerId, String calleeId, String roomId) {
        activeCalls.put(calleeId, new CallInfo(callerId, calleeId, roomId, CallStatus.STARTED));
    }

    public CallInfo acceptCall(String calleeId) {
        CallInfo info = activeCalls.get(calleeId);
        if (info != null) {
            info.status = CallStatus.ACCEPTED;
        }
        return info;
    }

    public void endCall(String userId) {
        activeCalls.entrySet().removeIf(entry ->
                entry.getValue().callerId.equals(userId) || entry.getValue().calleeId.equals(userId));
    }

    public CallInfo getCall(String userId) {
        return activeCalls.get(userId);
    }
}
