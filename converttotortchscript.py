import torch
from transformers import AutoModelForCausalLM, AutoTokenizer

# An instance of your model.
#model = torchvision.models.resnet18(pretrained=True)
checkpoint = "bigcode/santacoder"
model = AutoModelForCausalLM.from_pretrained(checkpoint, trust_remote_code=True).to()
device = "cpu" # for GPU usage or "cpu" for CPU usage

tokenizer = AutoTokenizer.from_pretrained(checkpoint)
inputs = tokenizer.encode("//java class to generate artificial intelligence:", return_tensors="pt").to(device)
outputs = model.generate(inputs)
# Switch the model to eval model
model.eval()



# An example input you would normally provide to your model's forward() method.
#example = torch.rand(1, 3, 224, 224)

# Use torch.jit.trace to generate a torch.jit.ScriptModule via tracing.
traced_script_module = torch.jit.trace(model, inputs)

# Save the TorchScript model
traced_script_module.save("traced_resnet_model.pt")