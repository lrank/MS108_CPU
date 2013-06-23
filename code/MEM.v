module MEM(
  clk,
  address_i,
  data_i,
  row_i,
  data_o,
  end_signal_i
);

input wire clk;
input wire [31:0] address_i;
input wire [31:0] data_i;
input wire [1:0] row_i;
output wire [31:0] data_o;

input wire end_signal_i;

reg read, write;

cache cache(
  .clock 		(clk),
  .reset	(1'b0),
  .write	(write),
  .read		(read),
  .address  (address_i),
  .data_in	(data_i),
  .data_out	(data_o),
  .hit (),
  .end_signal_i	(end_signal_i)
);

always @(row_i)
begin
  if (row_i == 2'b01)
    begin
      read = 1'b1;
      write = 1'b0;
    end
  if (row_i == 2'b10)
  	begin
      read = 1'b0;
      write = 1'b1;
  	end
 	else
 	 begin
 	   read=1'b1;
 	   write=1'b0;
 	 end
end

endmodule
