package com.eai.common.template;


public interface ITemplateDefinitionTransformer {
	public TemplateDefinition getTemplateDefinition(String currentKey, boolean isList, TemplateDefinitionMap currentTemplateContext);
}
