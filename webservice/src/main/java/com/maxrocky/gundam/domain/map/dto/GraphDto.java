package com.maxrocky.gundam.domain.map.dto;

import com.maxrocky.gundam.commons.model.biz.GraphEdge;
import com.maxrocky.gundam.commons.model.biz.GraphNode;

import java.util.List;

/**
 * Created by lizhipeng on 2016/7/7.
 */
public class GraphDto {
    private int mapId;
    private String villageId;
    private List<GraphNode> nodes;
    private List<GraphEdge> edges;
    private List<GraphNode> labeledNodes;
    private List<GraphNode> beacons;
    private List<GraphNode> charges;
    private String imgUrl;
    private String villageName;

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<GraphEdge> edges) {
        this.edges = edges;
    }

    public List<GraphNode> getLabeledNodes() {
        return labeledNodes;
    }

    public void setLabeledNodes(List<GraphNode> labeledNodes) {
        this.labeledNodes = labeledNodes;
    }

    public List<GraphNode> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<GraphNode> beacons) {
        this.beacons = beacons;
    }

    public List<GraphNode> getCharges() {
        return charges;
    }

    public void setCharges(List<GraphNode> charges) {
        this.charges = charges;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
