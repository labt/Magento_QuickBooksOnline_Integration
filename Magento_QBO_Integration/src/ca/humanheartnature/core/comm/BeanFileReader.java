/*
 * Copyright 2017 Paolo Villadarez
 *
 * This code cannot be used, copied, or redistributed without express consent from the
 * author. Please contact villadarez@gmail.com for permission to use this code.
 */
package ca.humanheartnature.core.comm;

import ca.humanheartnature.core.exception.FileReadException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Generates objects of type <code>T</code> from file streams
 * 
 * @param <T> Type of the object generated by the {@link #get} method
 */
public class BeanFileReader<T extends Serializable> implements Supplier<T>
{  
   /** File location of the java bean file to import into JVM */
   private String fileLocation;
   
   /**
    * @param fileLocation File location of the java bean file to import into JVM 
    */
   public BeanFileReader(String fileLocation)
   {
      if (fileLocation == null)
      {
         throw new IllegalArgumentException("Argument cannot be null");
      }
      
      this.fileLocation = fileLocation;
   }
   
   /**
    * De-serialize a file into an object of type <code>T</code>
    * 
    * @return Serializable object
    */
   @Override
   public T get()
   {  
      try(FileInputStream fileStream = new FileInputStream(fileLocation))
      {
         try(ObjectInputStream objectStream = new ObjectInputStream(fileStream))
         {
            return (T) objectStream.readObject();
         }
      }
      catch(IOException | ClassNotFoundException ex)
      {
         throw new FileReadException("Error encountered when reading " + fileLocation,ex);
      }
   }
}
