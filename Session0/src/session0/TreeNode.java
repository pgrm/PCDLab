package session0;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 20.09.12
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
public class TreeNode<T extends Comparable<T>> {
    private T data;
    private TreeNode<T> leftNode, rightNode;

    public TreeNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void insert(T data) {
        if (data.compareTo(this.data) < 0) {
            insertIntoLeftNode(data);
        } else {
            insertIntoRightNode(data);
        }
    }

    public void traverseInOrder(TraverseMap<T> map) {
        if (leftNode != null)
            leftNode.traverseInOrder(map);

        map.mapToNodeData(data);

        if (rightNode != null)
            rightNode.traverseInOrder(map);
    }

    private void insertIntoLeftNode(T data) {
        if (leftNode == null)
            leftNode = new TreeNode<T>(data);
        else
            leftNode.insert(data);
    }

    private void insertIntoRightNode(T data) {
        if (rightNode == null)
            rightNode = new TreeNode<T>(data);
        else
            rightNode.insert(data);
    }

    public interface TraverseMap<T> {
        void mapToNodeData(T data);
    }
}
