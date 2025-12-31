package com.ap.chat.common.protocol;

public enum PacketType {
    // auth
    REGISTER_REQ,
    REGISTER_RES,
    LOGIN_REQ,
    LOGIN_RES,

    // rooms
    CREATE_ROOM_REQ,
    JOIN_ROOM_REQ,
    LEAVE_ROOM_REQ,
    ROOMS_REQ,
    ROOMS_RES,
    USERS_REQ,
    USERS_RES,

    // chat
    CHAT_MSG_REQ,
    CHAT_EVENT,
    ROOM_EVENT,

    // file
    FILE_UPLOAD_REQ,
    FILE_UPLOAD_RES,
    FILE_DOWNLOAD_REQ,
    FILE_DOWNLOAD_RES,

    // export
    EXPORT_LAST_REQ,
    EXPORT_DATA_RES,

    // error
    ERROR
}