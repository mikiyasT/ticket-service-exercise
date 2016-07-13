. Additional Comments are added inside the code wher i added my modifactions.
  
1. I have added a Seat class to model a seat information.
   Class name is Seat.Java.

2. I have placed a hashMap to hold id and List of seats that are held by 
   a user. This can be used by any other usrs as well , so that we can have a global kind of repositry to store
   our reservation inforamation.

3. When a seat is reserved, timer  is set automatically with it.

4. Row by row scanning is used to find best rows.
   The first available rows are taken as rows to be used for reservation..
  If blocks are found they are reserved consecuetively , otherwise all those near the stage are picked and reseved



