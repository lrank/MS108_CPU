module MEM_WB(
  clk_i,
  data1_i,
  IR_i,
  data1_o,
  reg_num
);


input wire clk_i;
input wire [31:0] data1_i;
input wire [31:0] IR_i;
output reg [31:0] data1_o;
output reg [5:0] reg_num;

//`include "parameter.v"
parameter NIB_SIZE = 4;
parameter BYTE_SIZE = 8;
parameter WORD_SIZE = 16;
parameter MEM_SIZE = 1024 * 4;

parameter ALU_LW =    4'b0000;
parameter ALU_SW =    4'b0001;
parameter ALU_LI =    4'b0010;
parameter ALU_ADDU =  4'b0011;
parameter ALU_ADDIU = 4'b0100;
parameter ALU_SLL =   4'b0101;
parameter ALU_MUL =   4'b0110;
parameter ALU_BGE =   4'b0111;
parameter ALU_J =     4'b1000;
parameter ALU_MULI =  4'b1001;

parameter OP_ADD = 3'b000,
          OP_MUL = 3'b001,
          OP_SLL = 3'b010,
          OP_BGE = 3'b011;

always @(posedge clk_i)
begin
//  IR_o = IR_i;
  data1_o = data1_i;

  if (IR_i[31:28] == ALU_LW ||
  	  IR_i[31:28] == ALU_LI ||
  	  IR_i[31:28] == ALU_ADDU ||
  	  IR_i[31:28] == ALU_ADDIU ||
  	  IR_i[31:28] == ALU_SLL ||
  	  IR_i[31:28] == ALU_MUL ||
  	  IR_i[31:28] == ALU_MULI)
  	reg_num = {1'b1, IR_i[27:23]};
  else
  	reg_num = 6'b011111;
end

endmodule