module MUX32(
    data1_i,
    data2_i,
    select_i,
    data_o
);

input wire [31:0] data1_i;
input wire [31:0] data2_i;
input wire select_i;
output reg [31:0] data_o;

always @(select_i or data1_i or data2_i)
begin
  assign data_o= select_i? data2_i :data1_i;
end

endmodule 
