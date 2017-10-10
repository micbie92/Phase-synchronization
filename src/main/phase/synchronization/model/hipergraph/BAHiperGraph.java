package main.phase.synchronization.model.hipergraph;

import main.phase.synchronization.model.common.Pair;
import org.apache.log4j.Logger;
import main.phase.synchronization.model.common.Pair;
import main.phase.synchronization.model.oscillator.OscillatingVerticle;

import java.util.Collection;

/**
 * Created by Michał Bielecki on 21.08.17.
 * Faculty of Physics
 * Warsaw University of Technology
 */
public class BAHiperGraph extends AbstractHiperGraph {

    private static Logger LOGGER = Logger.getLogger(BAHiperGraph.class);

    public BAHiperGraph(){

    }

    @Override
    protected void addEdgesToNewVerticle(OscillatingVerticle newVerticle, int newEdgesCount, int stepIndex) {
        Collection<OscillatingVerticle> availabeVerticles = getAvailableVerticles(newVerticle);
        int edgesAdded = 0;
        int probablityDivider = 2*stepIndex*newEdgesCount;
        while( edgesAdded < newEdgesCount){
            for (OscillatingVerticle ov : availabeVerticles) {
                double neighborsCount =(double) graph.getNeighborCount(ov);
                double propablity = neighborsCount/probablityDivider;
                double random = Math.random();
                if(random<propablity){
                    createEdge(new Pair<>(ov, newVerticle));
                    edgesAdded++;
                    LOGGER.debug("Edge added: V1: "+ov+", V2: "+newVerticle);
                    break;
                }
            }
        }
    }

    @Override
    protected void initGraph(int initSize) {
        createInitVerticles(initSize);
        createInitEdge();
    }

    private void createInitEdge() {
        graph.getVertices().stream()
                .forEach(ov -> addInitEdges(ov));
    }

    private void addInitEdges(OscillatingVerticle ov) {
        Collection<OscillatingVerticle> availableVertex = getAvailableVerticles(ov);
        availableVertex.stream()
                .forEach(o -> createEdge(new Pair(o, ov)));

    }
}
