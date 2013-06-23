module ID_EX(
  clk_i,
  data1_i,
  data2_i,
  data3_i,
  data1_o,
  data2_o,
  data3_o,
  control_o,
  op_o,
  IR_i,
  IR_o,
  is_i,
  data_i
);

input wire clk_i;
input wire [31:0] data1_i;
input wire [31:0] data2_i;
input wire [31:0] data3_i;
output reg [31:0] data1_o;
output reg [31:0] data2_o;
output reg [31:0] data3_o;
output reg control_o;
output reg [2:0] op_o;
input wire [31:0] IR_i;
output reg [31:0] IR_o;
input wire [1:0] is_i;
input wire [31:0] data_i;

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
  IR_o = IR_i;
  if (IR_i[31:28] == ALU_LI ||
      IR_i[31:28] == ALU_J)
    begin
      data1_o = 32'b0;
      data2_o = 32'b0;
      data3_o = data3_i;
      op_o = OP_ADD;
      control_o = 1'b1;
    end
  else if (IR_i[31:28] == ALU_BGE)
    begin
      data1_o = data1_i; 
      data2_o = data3_i;
      data3_o = data2_i;
      op_o = OP_BGE; control_o = 1'b1;
    	 if (is_i[0] == 1'b1)
        data1_o = data_i;
      if (is_i[1] == 1'b1)
        data3_o = data_i;
    end
    else
    begin
      data1_o = data1_i; 
      data2_o = data2_i;
      data3_o = data3_i;
      case (IR_i[31:28])
        ALU_LW: fork op_o = OP_ADD; control_o = 1'b1; join
        ALU_SW: fork op_o = OP_ADD; control_o = 1'b1; join
        ALU_ADDU: fork op_o = OP_ADD; control_o = 1'b0; join
        ALU_ADDIU: fork op_o = OP_ADD; control_o = 1'b1; join
        ALU_SLL: fork op_o = OP_SLL; control_o = 1'b1; join
        ALU_MUL: fork op_o = OP_MUL; control_o = 1'b0; join
        ALU_MULI: fork op_o = OP_MUL; control_o = 1'b1; join
      endcase
      
  if (is_i[0] == 1'b1)
    data1_o = data_i;
  if (is_i[1] == 1'b1)
    data2_o = data_i;
    end
  
end

endmodule