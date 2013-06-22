`timescale 1ns/10ps
module pipeline ();
  reg clock;
  cpu cpu(.clk_i(clock));
  initial  
  begin
    clock=0;
  end
  
  always #5 clock=~clock;
  
  /*initial fork
  #0 reset=1'b1;
  #2 reset=1'b0;
  #3 write=1'b1;#3 data_in=32'b10111110111111111110101111101111; #3 address=32'b101000;
  #5 write=1'b0; #5 data_in=32'b101011; #5 address=32'b101000;
  #7 write=1'b1; #7 data_in=32'b111000; #7 address=32'b110000;
  #9 write=1'b0; #9 address=32'b101000;
  #11 address=32'b110000;
  #13 address=32'b0;
  #15 write=1'b1; #15 data_in=32'b10101011111; #15 address=32'b101000;
  join*/

endmodule
