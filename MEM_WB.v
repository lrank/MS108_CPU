module MEM_WB(
  clk_i,
  data1_i,
  data2_i,
  IR_i,
  data1_o,
  data2_o,
  control_o,
  reg_num
);


input wire clk_i;
input wire [31:0] data1_i;
input wire [31:0] data2_i;
input wire [31:0] IR_i;
output reg [31:0] data1_o;
output reg [31:0] data2_o;
output reg control_o;
output reg [5:0] reg_num;

`include "parameter.v"

always @(posedge clk_i)
begin
  data1_o = data1_i;
  data2_o = data2_i;
  if (IR_i[31:28] == ALU_LW)
  	control_o = 1'b0;
  else
  	control_o = 1'b1;

  if (IR_i[31:28] == ALU_LW ||
  	  IR_i[31:28] == ALU_LI ||
  	  IR_i[31:28] == ALU_ADDU ||
  	  IR_i[31:28] == ALU_ADDIU ||
  	  IR_i[31:28] == ALU_SLL ||
  	  IR_i[31:28] == ALU_MUL ||
  	  IR_i[31:28] == ALU_MULI)
  	reg_num = {1, IR_i[27:23]};
  else
  	reg_num = 2'b111111;
end

endmodule