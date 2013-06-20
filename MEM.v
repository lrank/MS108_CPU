module MEM(
  address_i,
  data_i,
  row_i,
  data_o
);

input wire [31:0] address_i;
input wire [31:0] data_i;
input wire [1:0] row_i;
output reg [31:0] data_o;

cache cache(
  .clk 		(clock)
  .reset	(0),
  .write	(read),
  .read		(write),
  .address  (address_i),
  .data_in	(data_i),
  .data_out	(data_o),
  .hit (0)
);

reg read, write;

always
begin
  if (row_i == 2'b01)
    begin
      read = 2'b1;
      write = 2'b0;
    end
  if (row_i == 2'b01)
  	begin
      read = 2'b0;
      write = 2'b1;
  	end
end

endmodule
