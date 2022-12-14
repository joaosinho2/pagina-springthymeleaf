package springthymeleaf.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springthymeleaf.dto.RequisicaoCliente;
import springthymeleaf.entities.Cliente;
import springthymeleaf.services.ClienteService;

//@requestMapping("\clientes") -> Ele vai Definir por padrào qual requisicao voce vai querer, assim evitando voce toda hora digitar /clientes
@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private boolean alertaClienteCadastrado = false;
    private boolean alertaClienteEditado = false;
    private boolean alertaClienteRemovido = false;
    private boolean erroCampoVazio = false;
    private boolean erroEmailExistente = false;
    private boolean erroCpfExistente = false;
    private boolean erroClienteNaoEncontrado = false;


    //Classe para criptografar a senha
    private BCryptPasswordEncoder passwordEnconder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private ClienteService clienteService;

    @GetMapping("")
    public ModelAndView pageClientes() {

        List<Cliente> clientes = clienteService.findAllClientes();

        ModelAndView mv = new ModelAndView("/clientes/index");
        mv.addObject("clientes", clientes);
        mv.addObject("alertaClienteRemovido", alertaClienteRemovido);
        mv.addObject("erroClienteNaoEncontrado", erroClienteNaoEncontrado);
        alertaClienteRemovido = false;
        erroClienteNaoEncontrado = false;
        return mv;
    }

    // Para o th:field e o th:object funcionar tem que estanciar o minha classe DTO
    // no meu @getMapping e também no @Post
    @GetMapping("/new")
    public String paginaCadastro(RequisicaoCliente r) {

        return "clientes/new";
    }

    // Estou Criando um metodo que vai receber minha classe entidade Clientes
    // neste caso eu chamei no metodo minha classe RequisicaoCliente para proteger
    // os dados! eu poderia chamar diretamente a classe Cliente
    @PostMapping("")
    public ModelAndView registerCliente(@Valid RequisicaoCliente requisicao, BindingResult erro) {
        // Igualando os dados da classe cliente com a classe requisicao, para proteger
        // os dados!

        Cliente cliente = requisicao.toCliente();

        if (erro.hasErrors()) {
            ModelAndView mv = new ModelAndView("clientes/new");
            return mv;

        }

        if (clienteService.isVerificadorCampoVazio(requisicao)) {
            ModelAndView mv = new ModelAndView("clientes/new");
            erroCampoVazio = true;
            mv.addObject("erroCampoVazio", erroCampoVazio);
            erroCampoVazio = false;
            return mv;
        }

        if (clienteService.isVerificadorEmailCadastrado(requisicao)) {
            ModelAndView mv = new ModelAndView("clientes/new");
            erroEmailExistente = true;
            mv.addObject("erroEmailExistente", erroEmailExistente);
            erroEmailExistente = false;
            return mv;
        }

        if (clienteService.isVerificadorCpfCadastrado(requisicao)) {
            ModelAndView mv = new ModelAndView("clientes/new");
            erroCpfExistente = true;
            mv.addObject("erroCpfExistente", erroCpfExistente);
            erroCpfExistente = false;
            return mv;
        }
        //Criptografando a senha antes de cadastrar o cliente!
        cliente.setSenha(passwordEnconder().encode(cliente.getSenha()));
        clienteService.saveCliente(cliente);
        alertaClienteCadastrado = true;
        //clienteRepository.save(cliente);
        return new ModelAndView("redirect:/clientes/" + cliente.getId());
    }

    // {id} -> criei uma variavel http que vai esperar um ID
    // @pathVariable -> Serve para transformar minha viriavel Long id na variavel do
    // http
    // Optional -> Com o OPTIONAL ele informa que é opcional e não obrigatório, no
    // meu caso é opcional ter o ID, mas caso não tenha não vai estourar um Erro!
    // Para Acessar uma variavel ou um valor pelo thymeleaf se usa o
    // ${cliente.senha}
    @GetMapping("/{id}")
    public ModelAndView detailsCliente(@PathVariable Long id) {
        Optional<Cliente> optional = this.clienteService.findClienteById(id);

        if (optional.isPresent()) {

            Cliente cliente = optional.get();
            ModelAndView mv = new ModelAndView("clientes/show");
            mv.addObject("cliente", cliente);
            mv.addObject("alertaClienteEditado", alertaClienteEditado);
            mv.addObject("alertaClienteCadastrado", alertaClienteCadastrado);
            alertaClienteEditado = false;
            alertaClienteCadastrado = false;
            return mv;

        } else {
            System.out.println("Cliente Não encontrado!" + id);
            erroClienteNaoEncontrado = true;
            return new ModelAndView("redirect:/clientes");
        }

    }

    //estou Usando o Optional.get() para pegar o valor da busca por ID e passar para o cliente
    @GetMapping("/{id}/edit")
    public ModelAndView pageEditCliente(@PathVariable Long id, RequisicaoCliente requisicao) {
        Optional<Cliente> optional = this.clienteService.findClienteById(id);

        if (optional.isPresent()) {
            Cliente cliente = optional.get();
            //aqui eu carrego todos valores de cliente no meu fromClientes, Ja feito um metodo para isso na classe DTO!
            requisicao.fromCliente(cliente);

            ModelAndView mv = new ModelAndView("clientes/edit");
            //Adicionando um objeto para conseguir utilizado dentro do html, com o valor cliente.getId()
            mv.addObject("clienteId", cliente.getId());

            return mv;

        } else {
            System.out.println("Cliente Não Encontrado!");
            erroClienteNaoEncontrado = true;
            return new ModelAndView("redirect:/clientes");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView editCliente(@PathVariable Long id, @Valid RequisicaoCliente requisicao, BindingResult bindingResult) /*throws IOException*/ {
        //URL url = new URL("viacep.com.br/ws/"+requisicao.getCep()+"/json/");
        //URLConnection connection = url.openConnection();
        //InputStream is = connection.getInputStream();
        //BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("clientes/edit");
            mv.addObject("clienteId", id);
            System.out.println(bindingResult);
            return mv;
        } else {

            Optional<Cliente> optional = this.clienteService.findClienteById(id);

            if (optional.isPresent()) {
                Cliente cliente = requisicao.toCliente(optional.get());

                this.clienteService.saveCliente(cliente);
                alertaClienteEditado = true;
                return new ModelAndView("redirect:/clientes/" + cliente.getId());

            } else {
                System.out.println("Cliente Não Encontrado!");
                erroClienteNaoEncontrado = true;
                return new ModelAndView("redirect:/clientes");
            }
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteCliente(@PathVariable Long id) {
        try {
            this.clienteService.deleteCliente(id);
            alertaClienteRemovido = true;
            return "redirect:/clientes";
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Cliente Nao Encontrado");
            erroClienteNaoEncontrado = true;
            return "redirect:/clientes";

        }
    }

}
