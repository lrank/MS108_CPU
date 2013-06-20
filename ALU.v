module ALU(
  src1,
  src2,
  op,
  result
  );

`include "parameter.v"

input wire [2:0] op;
input wire [31:0] src1;
input wire [31:0] src2;
output reg [31:0] result;

always
begin
  case (op)
    OP_ADD:
      assign result = src1 + src2;
    OP_MUL:
      assign result = src1 * src2;
    OP_BGE:
      assign result = src1 >= src2;
    OP_SLL:
      assign result = src1 << src2;
  endcase
end

endmodule