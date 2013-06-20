module EX_MEM(
  clk_i,
  data1_i,
  data2_i,
  IR_i,
  data1_o,
  data2_o,
  IR_o,
  control_o,
  row_o
);

input wire clk_i;
input wire [31:0] data1_i;
input wire [31:0] data2_i;
input wire [31:0] IR_i;
output reg [31:0] data1_o;
output reg [31:0] data2_o;
output reg [31:0] IR_o;
output reg control_o;
output reg [1:0]row_o; // 00 for nop, 01 for read, 10 for write

`include "parameter.v"

always @(posedge clk_i)
begin
  assign IR_o = IR_i;
  assign data1_o = data1_i;
  assign data2_o = data2_i;
  
  if (IR_o[31:28] == ALU_BGE ||
      IR_o[31:28] == ALU_J)
    control_o = 1'b1;
  else
    control_o = 1'b0;
  
  if (IR_o[31:28] == ALU_LW)
    row_o = 2'b01;
  else if (IR_o[31:28] == ALU_SW)
    row_o = 2'b10;
  else row_o = 2'b0;
end
endmodule
