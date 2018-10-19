package com.maxrocky.gundam.coreservice.controller;

import com.maxrocky.gundam.commons.algorithm.GPoint;
import com.maxrocky.gundam.commons.algorithm.GraphManager;
import com.maxrocky.gundam.commons.model.biz.GraphNode;
import com.maxrocky.gundam.commons.utility.GraphUtility;
import com.maxrocky.gundam.coreservice.common.ErrorApiResult;
import com.maxrocky.gundam.coreservice.common.SuccessApiResult;
import com.maxrocky.gundam.commons.core.MapCorrectionPoints;
import com.maxrocky.gundam.commons.model.biz.Graph;
import com.maxrocky.gundam.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yuer5 on 16/5/19.
 */
@RestController
@RequestMapping(value="/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @RequestMapping(value="/{villageId}", method= RequestMethod.GET)
    @ResponseBody
    public ModelMap GetGraph(@PathVariable String villageId) {
        Graph graph = mapService.GetGraph(villageId);
        return new SuccessApiResult(graph);
    }

    @RequestMapping(value="/", method= RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ModelMap SaveGraph(@RequestBody Graph graph) {
        try {
            mapService.SaveGraph(graph);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }

    @RequestMapping(value="/", method= RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ModelMap UpdateGraph(@RequestBody Graph graph) {
        try {
            mapService.UpdateGraph(graph);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }

    }

    @RequestMapping(value="/{villageId}/coordinate", method= RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ModelMap MapCorrection(@PathVariable String villageId, @RequestBody MapCorrectionPoints mappings){
        mappings.setVillageId(villageId);
        try {
            mapService.MapCoordinate(mappings);
            return new SuccessApiResult();
        } catch (Exception e) {
            return new ErrorApiResult("-1",e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{villageId}/findpath", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ModelMap GetGraphPath(@PathVariable String villageId, @RequestBody List<GraphNode> pointPair) {
        try {
            if (pointPair.size() != 2)
                throw new Exception("请检查起点和终点坐标");

            Graph graph = GraphUtility.regularisationMapInfo(mapService.GetGraph(villageId));
            GraphManager graphManager = new GraphManager();

            GraphNode start = pointPair.get(0);
            GraphNode end = pointPair.get(1);

            List<GraphNode> list = graphManager.DoFindPathByExternalPoint(graph, start, end);
            return new SuccessApiResult(list);
        } catch (Exception e) {
            return new ErrorApiResult("-1", e.getMessage(), e);
        }
    }

}
