# pip install -q transformers
#pip3 install torch torchvision torchaudio --extra-index-url https://download.pytorch.org/whl/cu116
#pip install petals

import sys
from transformers import AutoModelForCausalLM, AutoTokenizer

def extract_fim_part(s: str):
    # Find the index of
    start = s.find(FIM_MIDDLE) + len(FIM_MIDDLE)
    stop = s.find(EOD, start) or len(s)
    return s[start:stop]

#print ("params:"+str(len(sys.argv))+"\n"+str(sys.argv))
checkpoint = "bigcode/santacoder"
device = "cuda" # for GPU usage or "cpu" for CPU usage
FIM_PREFIX = "<fim-prefix>"
FIM_MIDDLE = "<fim-middle>"
FIM_SUFFIX = "<fim-suffix>"
FIM_PAD = "<fim-pad>"
EOD = "<|endoftext|>"
tokenizer = AutoTokenizer.from_pretrained(checkpoint)
tokenizer.add_special_tokens({
    "additional_special_tokens": [EOD, FIM_PREFIX, FIM_MIDDLE, FIM_SUFFIX, FIM_PAD],
    "pad_token": EOD,
})
model = AutoModelForCausalLM.from_pretrained(checkpoint, trust_remote_code=True).to(device)


if len(sys.argv)==1:
    prefix='//java class to generate artificial intelligence\npublic class MySuperClass{\n  public static void main(String[] args){'
    suffix='  }\n}'
    encoding=f"<fim-prefix>{prefix}<fim-suffix>{suffix}<fim-middle>"
    maxt=100


if len(sys.argv)==3:
    prefix=''
    suffix=''
    encoding=sys.argv[2]
    maxt=int(sys.argv[1])
if len(sys.argv)==4:
    prefix=sys.argv[2].replace('`',' ')
    suffix=sys.argv[3].replace('`',' ')
    encoding=f"<fim-prefix>{prefix}<fim-suffix>{suffix}<fim-middle>"
    maxt=int(sys.argv[1])
temp=1.7
inputs = tokenizer.encode(encoding, return_tensors="pt",padding=False, return_token_type_ids=False).to(device)
outputs = model.generate(input_ids=inputs,max_new_tokens=maxt,temperature=temp,top_p=0.95,do_sample=True,pad_token_id=tokenizer.pad_token_id)
out=(extract_fim_part(tokenizer.decode(tensor, skip_special_tokens=False)) for tensor in outputs)
print(*(out))

