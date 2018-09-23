import java.util.ArrayList;

/**
 * Created by Utkarsh on 8/30/18.
 * Revised by George Adams on 09/02/18.
 * Revised by Shivaram Gopal on 09/02/18.
 */

 
public class Stack<T>
{
    private T[] arr;
    private int size;
    private int capacity;

    public Stack(int capacity)
    {
        //Initialize your stack here
        this.arr = (T[]) new Object[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public boolean IsEmpty(){
        //Write your code here
        //Return whether the stack is empty or not
      return (arr[0] == null);

        //return false;             //remove this line and return the appropriate answer
    }

    public boolean push(T val){
        //Write your code here
        //Push the new element on the stack 
        //If the element was added successfully, return true
        //If the element was not added, return false
        if(size >= capacity - 1){
            resize();
        }

        arr[size] = val;
        size++;

        return (arr[size - 1] == val);             //remove this line and return the appropriate answer
    }

    public T pop() throws IndexOutOfBoundsException{
        //Write your code here
        // Method should return the top element of the stack
        // This removes the element from the stack
        // Incase the stack is empty, it should throw an error,
        // with the message "Empty Stack"

        if(size == 0 && arr[0] == null){

            throw new IndexOutOfBoundsException("Empty Stack");

        } else {

            T temp = arr[size - 1];
            arr[size - 1] = null;
            if(size > 0) size--;
            return temp;

        }

        //return null;    // add your own return statement
    }

    public int size()
    {
        //Write your code here
        //number of elements currently in the stack

        return size;               //remove this line and return the appropriate answer
    }

    public void resize(){
        T[] tempArr = (T[]) new Object[this.capacity * 2];
        for(int i = 0; i < this.capacity; i++){
            tempArr[i] = this.arr[i];
        }

        this.capacity = this.capacity * 2;
        this.arr = tempArr;
    }
}
