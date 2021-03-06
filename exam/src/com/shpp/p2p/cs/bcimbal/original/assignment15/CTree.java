package com.shpp.p2p.cs.bcimbal.original.assignment15;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

/* Class to perfor operations with huffman coding tree*/
class CTree {

    /* root node */
    private CNode root;
    /* symbols codes list */
    private ArrayList<SymbolCode> codesList;
    /* symbols codes array */
    private SymbolCode[] codes;

    /*******************************************************************************************************************
     * Constructor creates tree
     *
     * @param symbolFreqs list of symbols frequencies
     */
    CTree(LinkedList<CNode> symbolFreqs) {
        root = createTree(symbolFreqs);
        codesList = findCodes(root);

        /* symbols codes array */
        this.codes = new SymbolCode[256];
        for (SymbolCode symbol : codesList) {
            codes[symbol.getSymbol() & 0xff] = symbol;
        }
    }

    /*******************************************************************************************************************
     * Search for used symbols codes
     *
     * @param root tree root node
     * @return symbols codes list
     */
    private ArrayList<SymbolCode> findCodes(CNode root) {
        ArrayList<SymbolCode> codesTmp = new ArrayList<>();

        LinkedList<CNode> queue = new LinkedList<>();
        queue.add(root);
        /* BFS, DFS*/
        while (queue.size() > 0) {
            CNode currentNode = queue.poll();
            currentNode.setVisited(true);
            CNode[] leafs = currentNode.getLeafs();
            int nodeCode = currentNode.getCode();
            int bitsCount = currentNode.getBitCount();
            boolean allLeafsAreNull = true;

            for (int i = 0; i < leafs.length; i++) {
                if (leafs[i] != null && !leafs[i].isVisited()) {
                    leafs[i].setBitCount(bitsCount + 1);
                    leafs[i].setCode(nodeCode + (i << bitsCount));
                    queue.addLast(leafs[i]); //TODO replace to queue.addFirst(p) to use Depth-first search algorithm
                }
                if (leafs[i] != null) {
                    allLeafsAreNull = false;
                }
            }
            /* symbol found */
            if (allLeafsAreNull) {
                SymbolCode code = new SymbolCode(
                        currentNode.getByteValue(),
                        currentNode.getCode(),
                        currentNode.getBitCount());
                codesTmp.add(code);
            }
        }
        return codesTmp;
    }

    /*******************************************************************************************************************
     * External request for symbols codes
     *
     * @return array of symbols codes
     */
    public SymbolCode[] getCodes() {
        return this.codes;
    }

    /*******************************************************************************************************************
     * External request for tree root node
     *
     * @return tree root node
     */
    public CNode getRoot() {
        return this.root;
    }

    /*******************************************************************************************************************
     * Method to create huffman tree
     * @param freqs  list of symbols frequencies
     * @return tree root node
     */
    private CNode createTree(LinkedList<CNode> freqs) {
        LinkedList<CNode> tmp = (LinkedList<CNode>) freqs.clone();
        while (tmp.size() > 1) {
            CNode[] nodes = new CNode[2];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = tmp.poll();
            }

            CNode parent = new CNode(nodes[0].getFreq() + nodes[1].getFreq(),
                    (byte) 0, nodes);
            tmp.addFirst(parent);
            tmp.sort(Comparator.comparing(CNode::getFreq));
        }
        return tmp.poll();
    }
}