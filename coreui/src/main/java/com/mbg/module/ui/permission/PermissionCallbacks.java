package com.mbg.module.ui.permission;

import java.util.List;

public interface PermissionCallbacks {
    void onPermissionsGranted(int requestCodes, List<String> perms);
    void onPermissionsDenied(int requestCodes, List<String> perms);
}
