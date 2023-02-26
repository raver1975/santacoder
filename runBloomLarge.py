# pip install -q transformers
#pip3 install torch torchvision torchaudio --extra-index-url https://download.pytorch.org/whl/cu116

from petals import DistributedBloomForCausalLM

model = DistributedBloomForCausalLM.from_pretrained("bigscience/bloom-petals", tuning_mode="ptune", pre_seq_len=16)
# Embeddings & prompts are on your device, BLOOM blocks are distributed across the Internet
encoding=sys.argv[2]
maxt=int(sys.argv[1])
inputs = tokenizer(encoding, return_tensors="pt")["input_ids"]
outputs = model.generate(inputs, max_new_tokens=maxt)
print(tokenizer.decode(outputs[0]))  # A cat sat on a mat...
