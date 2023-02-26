# pip install -q transformers
#pip3 install torch torchvision torchaudio --extra-index-url https://download.pytorch.org/whl/cu116

import sys
from transformers import AutoModelForCausalLM, AutoTokenizer
# bloom-560m
# bloom-1b1
# bloom-1b7
# bloom-3b
# bloom-7b1
# bloom (176B parameters)
checkpoint = "bigscience/bloom-1b1"
device = "cuda" # for GPU usage or "cpu" for CPU usage
tokenizer = AutoTokenizer.from_pretrained(checkpoint)
model = AutoModelForCausalLM.from_pretrained(checkpoint, trust_remote_code=True).to(device)
encoding=sys.argv[2]
maxt=int(sys.argv[1])
temp=1.5
inputs = tokenizer.encode(encoding, return_tensors="pt",padding=False, return_token_type_ids=False).to(device)
outputs = model.generate(input_ids=inputs,max_new_tokens=maxt,temperature=temp,top_p=0.95,do_sample=True,pad_token_id=tokenizer.pad_token_id)
out=(tokenizer.decode(tensor, skip_special_tokens=False) for tensor in outputs)
print(*(out))

