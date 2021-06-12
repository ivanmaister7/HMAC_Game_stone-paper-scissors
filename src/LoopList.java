class LoopList<T> {

    private Node _first;
    private Node _last;
    private int _size;

    public LoopList() {
        _first = null;
        _last = null;
        _size = 0;
    }

    public int getSize() {
        return _size;
    }

    public void add(T data) {
        Node current = _first;

        if (current == null) {
            _first = new Node(data);
            _size++;
            return;
        }
        if(current.getNext() == null){
            _last = new Node(data);
            _first.setNext(_last);
            _first.set_prev(_last);
            _last.setNext(_first);
            _last.set_prev(_first);
            _size++;
            return;
        }

        current = _last;
        _last = new Node(data);
        current.get_prev().setNext(current);
        current.setNext(_last);
        _last.set_prev(current);
        _first.set_prev(_last);
        _last.setNext(_first);
        _size++;
    }

    public void add(T[] array) {
        for (T data : array) {
            add(data);
        }
    }

    public Node<T> get(int i){
        Node<T> current = _first;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }
        return current;
    }

    public void remove(T data) {
        Node current = _first;
        Node next = current.getNext();

        if (_first.getData().equals(data)) {
            if (_size == 1) {
                _first.setData(null);
                _size--;
                return;
            }
            _first.setData(null);
            _first = _first.getNext();
            _size--;
            return;
        }

        while (next != null) {
            if (next.getData().equals(data)) {
                current.setNext(next.getNext());
                next = null;
                _size--;
                return;
            }
            current = next;
            next = current.getNext();
        }
    }

    public void show(LoopList<T> list){
        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.get(i).getData());
        }
    }

    public int distanceBetween(Node<T> a, Node<T> b){
        int i = 0;
        while(!a.equals(b)){
            a = a.getNext();
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        LoopList<Integer> li = new LoopList<Integer>();
        li.add(1);
        li.add(2);
        li.add(3);
        li.add(4);
        li.show(li);
        System.out.println(li.distanceBetween(li.get(3), li.get(1)));
    }
}

class Node<T> {

    private T _data;
    private Node _next;
    private Node _prev;

    public Node(T data) {
        _data = data;
        _next = null;
        _prev = null;
    }

    public void setData(T data) {
        _data = data;
    }

    public T getData() {
        return _data;
    }

    public void setNext(Node next) {
        this._next = next;
    }

    public Node getNext() {
        return _next;
    }

    public Node get_prev() {
        return _prev;
    }

    public void set_prev(Node _prev) {
        this._prev = _prev;
    }
}