package net.arcticforestmc.SlimePuncher.Base;

public class SerializedGamePlayer {
    //used for data storage and covnersion of object to storable characters for sql and caching.
    
    public int bits;
    public int xpBits;
    public int playerLevel;
    public String trackingStageIdentifier;
    public int stageTile;
    public String ownerUUID;

    
    public SerializedGamePlayer(GamePlayer source) {
        bits = source.getBits();
        xpBits = source.getXpBits();
        stageTile = source.getStageTile();
        playerLevel = source.getPlayerLevel();
        ownerUUID = source.getOwner().getUniqueId().toString();
        trackingStageIdentifier = source.getStageTree().getTracking().getStageIdentifier()[0]+
                                  "_"+
                                  source.getStageTree().getTracking().getStageIdentifier()[1];
    }
}
