package dcom.cave_service.business.utils;

import dcom.sharedlibrarydcom.shared.CavePermissions;
import dcom.sharedlibrarydcom.shared.ChannelPermissions;
import dcom.sharedlibrarydcom.shared.ChannelPermissionsCache;
import dcom.sharedlibrarydcom.shared.UserRolesAndPermissionsCache;
import org.springframework.stereotype.Component;


@Component
public class PermissionsUtils {
    public boolean canSeeChannel(UserRolesAndPermissionsCache userRolesAndPermissionsCache, String channelId){
        return checkChannelPermission(userRolesAndPermissionsCache, CavePermissions.SEE_CHANNELS, ChannelPermissions.SEE_CHANNEL, channelId);
    }

    public boolean canCreateInvite(UserRolesAndPermissionsCache userRolesAndPermissionsCache){
        return hasPermission(userRolesAndPermissionsCache.getCavePermissions(), CavePermissions.CREATE_INVITES);
    }

    public boolean canManageChannel(UserRolesAndPermissionsCache userRolesAndPermissionsCache){
        return hasPermission(userRolesAndPermissionsCache.getCavePermissions(), CavePermissions.MANAGE_CHANNELS);
    }

    public boolean isOwner(UserRolesAndPermissionsCache userRolesAndPermissionsCache){
        return hasPermission(userRolesAndPermissionsCache.getCavePermissions(), CavePermissions.OWNER);
    }

    public boolean isAdmin(UserRolesAndPermissionsCache userRolesAndPermissionsCache){
        return hasPermission(userRolesAndPermissionsCache.getCavePermissions(), CavePermissions.ADMIN);
    }

    public boolean canManageRoles(UserRolesAndPermissionsCache userRolesAndPermissionsCache){
        return hasPermission(userRolesAndPermissionsCache.getCavePermissions(), CavePermissions.MANAGE_ROLES);
    }

    private boolean checkChannelPermission(UserRolesAndPermissionsCache userRolesAndPermissionsCache, int cavePermissions, int channelPermission, String channelId) {
        ChannelPermissionsCache channelPermissionsCache = userRolesAndPermissionsCache.getChannelPermissionsCacheHashMap().get(channelId);
        boolean isCaveAllowed = hasPermission(userRolesAndPermissionsCache.getCavePermissions(), cavePermissions);
        if (channelPermissionsCache == null) {
            return isCaveAllowed;
        }

        boolean isAllowed = hasPermission(channelPermissionsCache.getAllow(), channelPermission);
        boolean isDenied = hasPermission(channelPermissionsCache.getDeny(), channelPermission);

        if (isAllowed) {
            return true;
        } else if (isDenied) {
            return false;
        } else {
            return isCaveAllowed;
        }
    }

    private boolean hasPermission(int permissions, int permission) {
        if ((permissions & CavePermissions.OWNER) == CavePermissions.OWNER)
        {
            return true;
        }
        return (permissions & permission) == permission;
    }
}
