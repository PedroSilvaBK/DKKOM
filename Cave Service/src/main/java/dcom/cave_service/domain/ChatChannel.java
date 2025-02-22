package dcom.cave_service.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class ChatChannel extends Channel {
    private String description;
    private List<ChannelRole> chatChannelRoles;
}
