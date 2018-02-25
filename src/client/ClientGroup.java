package client;

import main.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ClientGroup {
    private List<Client> members;
    private ClientSeatPreference preference;

    public ClientGroup() {
        members = new ArrayList<>();
        preference = new ClientSeatPreference(null, null);
    }

    public void addMember(Client member) {
        checkMaximumMemberSize();
        members.add(member);
    }

    private void checkMaximumMemberSize() {
        if (members.size() >= Configuration.instance.maximumGroupSize) {
            throw new IllegalStateException("maximum group size is " + Configuration.instance.maximumGroupSize);
        }
    }

    public boolean consistsOfOnlineClients() {
        for (Client member : members) {
            if (!member.isOnlineClient()) {
                return false;
            }
        }
        return true;
    }

    public List<Client> getMembers() {
        return members;
    }

    public int getSize() {
        return members.size();
    }

    public ClientSeatPreference getPreference() {
        return preference;
    }
}
