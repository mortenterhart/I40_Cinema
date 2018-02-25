package client;

import block.Block;
import logging.Logger;
import main.Configuration;
import seat.SeatSection;

import java.util.ArrayList;
import java.util.List;

public class ClientGroup {
    private static Block leftBlock;

    private List<Client> members;
    private ClientSeatPreference preference;

    public ClientGroup() {
        members = new ArrayList<>();
        preference = generateRandomPreference();
    }

    public static void setLeftBlock(Block leftBlock) {
        ClientGroup.leftBlock = leftBlock;
    }

    private ClientSeatPreference generateRandomPreference() {
        Block randomBlock = chooseBlock(Configuration.instance.mersenneTwister.nextInt(3), leftBlock);
        int randomSectionIndex = Configuration.instance.mersenneTwister.nextInt(randomBlock.getSections().size());
        SeatSection randomSection = randomBlock.getSections().get(randomSectionIndex);

        return new ClientSeatPreference(randomBlock, randomSection);
    }

    // COR
    private Block chooseBlock(int depth, Block block) {
        if (depth == 0) {
            return block;
        }

        return chooseBlock(depth - 1, block.getSuccessor());
    }

    public boolean acceptsOffer() {
        return Configuration.instance.mersenneTwister.nextBoolean(Configuration.instance.offerAcceptingProbability);
    }

    public void leaveCinema() {
        Logger.instance.log("Leaving cinema ...");
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
