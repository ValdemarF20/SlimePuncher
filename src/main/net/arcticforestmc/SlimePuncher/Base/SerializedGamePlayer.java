package net.arcticforestmc.SlimePuncher.Base;

public class SerializedGamePlayer {
    //used for data storage and covnersion of object to storable characters for sql and caching.
    
    public int bits;
    public int xpBits;
    public String trackingStageIdentifier;
    public int stageTile;
    public String ownerUUID;

    
    public SerializedGamePlayer(GamePlayer source) {
        bits = source.getBits();
        xpBits = source.getXpBits();
        stageTile = source.getStageTile();
        ownerUUID = source.getOwner().getUniqueId().toString();
        trackingStageIdentifier = source.getStageTree().getTracking().getStageIdentifier()[0]+
                                  "_"+
                                  source.getStageTree().getTracking().getStageIdentifier()[1];
    }

    public SerializedGamePlayer(String ownerUUID, String trackingStageIdentifier, int bits, int xpBits, int stageTile) {
        this.bits = bits;
        this.xpBits = xpBits;
        this.stageTile = stageTile;
        this.ownerUUID = ownerUUID;
        this.trackingStageIdentifier = trackingStageIdentifier; 
    }
}
