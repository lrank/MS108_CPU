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
  IR_o
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

`include "parameter.v"

always @(posedge clk_i)
begin
  assign IR_o = IR_i;
  if (IR_i[31:28] == ALU_LI ||
      IR_i[31:28] == ALU_J)
    begin
      assign data1_o = 32'b0;
      assign data2_o = data2_i;
      assign data3_o = 32'b0;
      assign op_o = OP_ADD;
      assign control_o = 1'b1;
    end
  else if (IR_i[31:28] == ALU_BGE)
    begin
      assign data1_o = data2_i; 
      assign data2_o = data1_i;
      assign data3_o = 32'b0;
      op_o = OP_BGE; control_o = 1'b0;
    end
  else
  begin
    assign data1_o = data1_i; 
    assign data2_o = data2_i;
    assign data3_o = data3_i;
    case (IR_i[31:28])
      ALU_LW: fork op_o = OP_ADD; control_o = 1'b1; join
      ALU_SW: fork op_o = OP_ADD; control_o = 1'b1; join
      ALU_ADDU: fork op_o = OP_ADD; control_o = 1'b0; join
      ALU_ADDIU: fork op_o = OP_ADD; control_o = 1'b1; join
      ALU_SLL: fork op_o = OP_SLL; control_o = 1'b1; join
      ALU_MUL: fork op_o = OP_MUL; control_o = 1'b0; join
      ALU_MULI: fork op_o = OP_MUL; control_o = 1'b1; join
    endcase
  end
end

endmodule